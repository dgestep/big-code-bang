package ${topLevelDomain}.${companyName}.${productName}.model.service.security;

import ${topLevelDomain}.${companyName}.${productName}.model.criteria.UserSearchCriteriaData;
import ${topLevelDomain}.${companyName}.${productName}.model.data.MessageData;
import ${topLevelDomain}.${companyName}.${productName}.model.data.UserCredential;
import ${topLevelDomain}.${companyName}.${productName}.model.data.UserData;
import ${topLevelDomain}.${companyName}.${productName}.model.data.UserProfile;
import ${topLevelDomain}.${companyName}.${productName}.model.data.UserToken;
import ${topLevelDomain}.${companyName}.${productName}.model.enumeration.message.GeneralMessage;
import ${topLevelDomain}.${companyName}.${productName}.model.enumeration.message.SecurityMessage;
import ${topLevelDomain}.${companyName}.${productName}.model.enumeration.message.UserMessage;
import ${topLevelDomain}.${companyName}.${productName}.model.exception.DataInputException;
import ${topLevelDomain}.${companyName}.${productName}.model.log.LogFactory;
import ${topLevelDomain}.${companyName}.${productName}.model.log.Logger;
import ${topLevelDomain}.${companyName}.${productName}.model.repository.CrudRepository;
import ${topLevelDomain}.${companyName}.${productName}.model.repository.user.PasswordValidator;
import ${topLevelDomain}.${companyName}.${productName}.model.repository.user.UserRepository;
import ${topLevelDomain}.${companyName}.${productName}.model.service.SecurityHelper;
import org.apache.commons.lang3.StringUtils;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.exceptions.EncryptionInitializationException;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.UUID;

/**
 * Authenticates a user in the application.
 *
 * @author dougestep
 */
@Service("AuthenticationService") //NOCHECKSTYLE coupling
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final String DELIM = "|";

    @Resource(name = "UserRepository")
    private UserRepository userRepository;

    @Resource(name = "CrudRepository")
    private CrudRepository<UserToken> userTokenRepository;

    @Resource(name = "JasyptPasswordValidator")
    private PasswordValidator passwordValidator;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public UserData authenticate(final String emailAddress, final String password, final boolean autoLogin) {
        final UserProfile user = new UserProfile();
        user.setEmailAddress(emailAddress);

        final UserCredential credential = new UserCredential();
        credential.setPassword(password);
        user.setUserCredential(credential);

        if (autoLogin) {
            return doAutoLogin(user);
        }

        assertDataReadyToAuthenticate(user);

        final UserProfile profile = findProfile(user);
        final UserCredential storedCredential = profile.getUserCredential();

        final UserCredential suppliedCredential = user.getUserCredential();
        final String plainTextPassword = suppliedCredential == null ? "" : suppliedCredential.getPassword();

        SecurityHelper.assertCurrentPassword(storedCredential, plainTextPassword, passwordValidator);
        updateUserProfile(profile);

        final String encryptedToken = addToken(profile);
        return populateUserDataFrom(profile, encryptedToken);
    }

    /**
     * Logs in the user without validating credentials.
     *
     * @param user the user.
     * @return the logged in user information.
     */
    private UserData doAutoLogin(final UserProfile user) {
        final MessageData md = assertEmailSupplied(user);
        if (md != null) {
            // validation errors
            throw new DataInputException(md);
        }
        final UserProfile profile = findProfile(user);
        updateUserProfile(profile);

        final String encryptedToken = addToken(profile);
        return populateUserDataFrom(profile, encryptedToken);
    }

    /**
     * Generates a new token and adds it to the data store.
     *
     * @param userProfile the user profile information.
     * @return the newly generated token.
     */
    private String addToken(final UserProfile userProfile) {
        final String tokenUuid = UUID.randomUUID().toString();
        final Date createTs = new Date();
        final long timestamp = createTs.getTime();
        Timestamp sqlTimestamp = new Timestamp(timestamp);

        // saveDocumentAssociation the token data
        final UserToken userToken = new UserToken();
        userToken.setTokenUuid(tokenUuid);
        userToken.setUserUuid(userProfile.getUuid());
        userToken.setEmailAddress(userProfile.getEmailAddress());
        userToken.setCreateTs(sqlTimestamp);
        userToken.setLastModifiedTs(sqlTimestamp);
        userTokenRepository.save(userToken);

        // create the encrypted token
        final String token = tokenUuid + DELIM + userProfile.getEmailAddress() + DELIM + timestamp;
        final StandardPBEStringEncryptor encryptor = createEncryptor();
        return encryptor.encrypt(token);
    }

    /**
     * Updates the user profile with the last modified timestamps.
     *
     * @param profile the updated user profile.
     */
    private void updateUserProfile(final UserProfile profile) {
        profile.setLastLoggedTs(new Timestamp(new Date().getTime()));
        userRepository.save(profile);
    }

    /**
     * Retrieves the user profile.
     *
     * @param user the user to retrieve.
     * @return the profile.
     */
    private UserProfile findProfile(final UserProfile user) {
        final UserProfile profile = SecurityHelper.searchByEmailAddress(user, userRepository);
        if (profile == null) {
            // not on record
            throw new DataInputException(new MessageData(GeneralMessage.G003, "email address"));
        }

        if (!profile.getActiveFlag().equals("Y")) {
            // not active
            throw new DataInputException(new MessageData(UserMessage.U003));
        }
        return profile;
    }

    /**
     * Asserts the supplied user information is ready to authenticate.
     *
     * @param user the user.
     */
    private void assertDataReadyToAuthenticate(final UserProfile user) {
        final List<MessageData> messages = new ArrayList<>();
        MessageData md = assertEmailSupplied(user);
        if (md != null) {
            messages.add(md);
        }

        md = assertPasswordSupplied(user);
        if (md != null) {
            messages.add(md);
        }

        if (messages.size() > 0) {
            // validation errors
            throw new DataInputException(messages);
        }
    }

    /**
     * Asserts the user email address is supplied.
     *
     * @param user the user.
     * @return the message if not supplied.
     */
    private MessageData assertEmailSupplied(final UserProfile user) {
        MessageData md = null;
        if (user == null || StringUtils.isEmpty(user.getEmailAddress())) {
            md = new MessageData(GeneralMessage.G001, "email address");
        }
        return md;
    }

    /**
     * Asserts the user password is supplied.
     *
     * @param user the user.
     * @return the message if not supplied.
     */
    private MessageData assertPasswordSupplied(final UserProfile user) {
        MessageData md = null;
        if (user == null || user.getUserCredential() == null || StringUtils
                .isEmpty(user.getUserCredential().getPassword())) {
            md = new MessageData(GeneralMessage.G001, "password");
        }
        return md;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public UserData applyToken(final String token) {
        if (token == null) {
            return null;
        }

        UserToken userToken = null;
        try {
            userToken = extractDataFromToken(token);
            if (userToken != null && !validateToken(userToken)) {
                userToken = null;
            }
        }
        catch (EncryptionOperationNotPossibleException | EncryptionInitializationException encryptionException) {
            // leave userToken as null
            final Logger logger = LogFactory.getLogger();
            logger.error(String.format("Unable to decrypt user token: %s", token));
        }
        if (userToken == null) {
            return null;
        }

        final UserProfile userProfile = getUserProfile(userToken.getEmailAddress());
        return populateUserDataFrom(userProfile, token);
    }

    /**
     * Populates and returns the user information related to the supplied user and token.
     *
     * @param userProfile the user profile.
     * @param token       the token.
     * @return the user data or null if no user profile exists for the supplied email.
     */
    private UserData populateUserDataFrom(final UserProfile userProfile, final String token) {
        final UserData userData;
        if (userProfile == null) {
            userData = null;
        } else {
            userData = new UserData();
            userData.setEmailAddress(userProfile.getEmailAddress());
            userData.setToken(token);
            userData.setRole(userProfile.getRole().getValue());
            userData.setUserUuid(userProfile.getUuid());
        }
        return userData;
    }

    /**
     * Returns the user profile associated with the supplied email address.
     *
     * @param emailAddress the email address.
     * @return the user profile or null if not found.
     */
    private UserProfile getUserProfile(final String emailAddress) {
        final UserSearchCriteriaData criteria = new UserSearchCriteriaData();
        criteria.setEmailAddress(emailAddress);
        final List<UserProfile> profiles = userRepository.search(criteria);
        return profiles.isEmpty() ? null : profiles.get(0);
    }

    /**
     * Decrypts and extracts the data points from within the token.
     *
     * @param token the token.
     * @return an instance of {@link UserToken}.
     */
    private UserToken extractDataFromToken(final String token) {
        final StandardPBEStringEncryptor encryptor = createEncryptor();
        final String decryptedToken = encryptor.decrypt(token);

        final UserToken userToken;
        final StringTokenizer parts = new StringTokenizer(decryptedToken, DELIM);
        if (parts.countTokens() == 3) { //NOCHECKSTYLE
            userToken = new UserToken();
            userToken.setTokenUuid(parts.nextToken());
            userToken.setEmailAddress(parts.nextToken());

            final Timestamp timestamp = new Timestamp(Long.parseLong(parts.nextToken()));
            userToken.setCreateTs(timestamp);
        } else {
            userToken = null;
            final Logger logger = LogFactory.getLogger();
            logger.error(String.format("Invalid user token: %s", token));
        }
        return userToken;
    }

    /**
     * Validates that the supplied token exists and updates its modified date.
     *
     * @param userToken the token data.
     * @return true if valid.
     */
    private boolean validateToken(final UserToken userToken) {
        final boolean valid;
        final String uuid = userToken.getTokenUuid();
        final UserToken storedToken = userTokenRepository.retrieve(UserToken.class, uuid);
        if (storedToken != null) {
            final Timestamp modifiedTime = new Timestamp(new Date().getTime());
            storedToken.setLastModifiedTs(modifiedTime);
            userTokenRepository.save(storedToken);

            userToken.setLastModifiedTs(modifiedTime);
            valid = true;
        } else {
            final Logger logger = LogFactory.getLogger();
            logger.error(String.format("Token identified by %s is no longer available", uuid));
            valid = false;
        }
        return valid;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void invalidate(final String token) {
        if (StringUtils.isEmpty(token)) {
            final MessageData messageData = new MessageData(SecurityMessage.SC003);
            throw new DataInputException(messageData);
        }

        final Logger logger = LogFactory.getLogger();

        try {
            final UserToken userToken = extractDataFromToken(token);
            if (userToken == null) {
                logger.error(String.format(SecurityMessage.SC002.getMessage(), "[UNKNOWN]", token));
                return;
            }

            final String uuid = userToken.getTokenUuid();
            if (userTokenRepository.retrieve(UserToken.class, uuid) == null) {
                logger.error(String.format(SecurityMessage.SC002.getMessage(), uuid, token));
            } else {
                userTokenRepository.delete(UserToken.class, uuid);
            }
        }
        catch (EncryptionOperationNotPossibleException | EncryptionInitializationException encryptionException) {
            logger.error(String.format("Unable to decrypt user token: %s", token));
        }
    }

    /**
     * Creates an instance of the encryptor.
     *
     * @return the instance.
     */
    private StandardPBEStringEncryptor createEncryptor() {
        final StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword("UDRI-DEFAULT-PW");
        return encryptor;
    }
}
