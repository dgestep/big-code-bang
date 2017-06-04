package ${topLevelDomain}.${companyName}.${productName}.model.service.user;

import ${topLevelDomain}.${companyName}.${productName}.model.ConfigConstant;
import ${topLevelDomain}.${companyName}.${productName}.model.criteria.UserSearchCriteriaData;
import ${topLevelDomain}.${companyName}.${productName}.model.data.LookupKeyValue;
import ${topLevelDomain}.${companyName}.${productName}.model.data.LookupKeyValuePK;
import ${topLevelDomain}.${companyName}.${productName}.model.data.MessageData;
import ${topLevelDomain}.${companyName}.${productName}.model.data.UserCredential;
import ${topLevelDomain}.${companyName}.${productName}.model.data.UserProfile;
import ${topLevelDomain}.${companyName}.${productName}.model.enumeration.Role;
import ${topLevelDomain}.${companyName}.${productName}.model.enumeration.message.GeneralMessage;
import ${topLevelDomain}.${companyName}.${productName}.model.enumeration.message.UserMessage;
import ${topLevelDomain}.${companyName}.${productName}.model.exception.DataInputException;
import ${topLevelDomain}.${companyName}.${productName}.model.repository.mail.MailRepository;
import ${topLevelDomain}.${companyName}.${productName}.model.repository.user.PasswordGeneratorRepository;
import ${topLevelDomain}.${companyName}.${productName}.model.repository.user.PasswordValidator;
import ${topLevelDomain}.${companyName}.${productName}.model.repository.user.UserCredentialRepository;
import ${topLevelDomain}.${companyName}.${productName}.model.repository.user.UserCredentialValidator;
import ${topLevelDomain}.${companyName}.${productName}.model.repository.user.UserRepository;
import ${topLevelDomain}.${companyName}.${productName}.model.service.EntityAssertion;
import ${topLevelDomain}.${companyName}.${productName}.model.service.SecurityHelper;
import ${topLevelDomain}.${companyName}.${productName}.model.service.lookup.LookupKeyValueService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

/**
 * Allows clients to add, update, and delete user information.
 *
 * @author ${codeAuthor}.
 */
@Service("UserService") // NOCHECKSTYLE coupling
public class UserServiceImpl implements UserService {
    private static final String DEFAULT_PASSWORD = "StarWars1977";

    @Resource(name = "UserRepository")
    private UserRepository userRepository;

    @Resource(name = "UserCredentialRepository")
    private UserCredentialRepository userCredentialRepository;

    @Resource(name = "UserCredentialValidator")
    private UserCredentialValidator userCredentialValidator;

    @Resource(name = "JasyptPasswordValidator")
    private PasswordValidator passwordValidator;

    @Resource(name = "LookupKeyValueService")
    private LookupKeyValueService lookupKeyValueService;

    @Resource(name = "PasswordGeneratorRepository")
    private PasswordGeneratorRepository passwordGeneratorRepository;

    @Resource(name = "MailRepository")
    private MailRepository emailRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void add(final UserProfile data) {
        if (data == null) {
            final MessageData message = new MessageData(GeneralMessage.G002, "user profile");
            throw new DataInputException(message);
        }

        if (data.getUuid() == null) {
            data.setUuid(UUID.randomUUID().toString());
        }

        setInitialValues(data);

        EntityAssertion entity = new EntityAssertion();
        entity.evaluate(data);

        final List<UserProfile> results = searchByEmail(data);
        if (results.size() > 0) {
            final MessageData message = new MessageData(UserMessage.U007, data.getEmailAddress());
            throw new DataInputException(message);
        }

        userRepository.add(data);

        UserCredential userCredential = data.getUserCredential();
        if (userCredential == null || StringUtils.isEmpty(userCredential.getPassword())) {
            userCredential = new UserCredential(data.getUuid());
            userCredential.setPassword(DEFAULT_PASSWORD);
        }

        userCredential.setUuid(data.getUuid());
        userCredential.setLastModifiedTs(data.getLastModifiedTs());

        final String plainTextPassword = userCredential.getPassword();
        final String encryptedPassword = encryptPassword(plainTextPassword);
        userCredential.setPassword(encryptedPassword);

        userCredentialRepository.add(userCredential);
    }

    /**
     * Defaults values if not supplied.
     *
     * @param user the user profile.
     */
    private void setInitialValues(final UserProfile user) {
        if (user.getCreateTs() == null) {
            user.setCreateTs(new Timestamp(new Date().getTime()));
        }
        if (user.getLastModifiedTs() == null) {
            user.setLastModifiedTs(new Timestamp(new Date().getTime()));
        }
        if (user.getActiveFlag() == null) {
            user.setActiveFlag("Y");
        }
        if (user.getRole() == null) {
            user.setRole(Role.USER);
        }
    }

    /**
     * Issues a search by email address.
     *
     * @param user the user profile.
     * @return the results.
     */
    private List<UserProfile> searchByEmail(final UserProfile user) {
        UserSearchCriteriaData criteria = new UserSearchCriteriaData();
        criteria.setEmailAddress(user.getEmailAddress());
        return userRepository.search(criteria);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void save(final UserProfile data) {
        if (data == null || data.getUuid() == null) {
            return;
        }
        final UserProfile retr = userRepository.retrieve(UserProfile.class, data.getUuid());
        if (retr == null) {
            return;
        }

        assertProfileNotExistUsingChangedEmailAddress(retr, data);

        final UserProfile updt = setDefaultValuesForUpdate(retr, data);

        EntityAssertion entity = new EntityAssertion();
        entity.evaluate(updt);

        userRepository.save(updt);
    }

    /**
     * Sets default values in preparation for update.
     *
     * @param retr the user profile prior to being updated.
     * @param user the user profile being updated.
     * @return the updated profile.
     */
    @SuppressWarnings("PMD.NPathComplexity") // NOCHECKSTYLE NPath Complexity
    private UserProfile setDefaultValuesForUpdate(final UserProfile retr, final UserProfile user) {
        if (user.getActiveFlag() != null) {
            if (!user.getActiveFlag().equals("Y") && !user.getActiveFlag().equals("N")) {
                retr.setActiveFlag("Y");
            } else {
                retr.setActiveFlag(user.getActiveFlag());
            }
        }
        if (user.getCreateTs() != null) {
            retr.setCreateTs(new Timestamp(new Date().getTime()));
        }
        if (user.getEmailAddress() != null) {
            retr.setEmailAddress(user.getEmailAddress());
        }
        if (user.getLastLoggedTs() != null) {
            retr.setLastLoggedTs(user.getLastLoggedTs());
        }
        if (user.getRole() != null) {
            retr.setRole(user.getRole());
        }
        if (user.getFirstName() != null) {
            retr.setFirstName(user.getFirstName());
        }
        if (user.getLastName() != null) {
            retr.setLastName(user.getLastName());
        }

        retr.setLastModifiedTs(new Timestamp(new Date().getTime()));
        return retr;
    }

    /**
     * Asserts that an existing user profile doesn't exist using the email address supplied in the changing profile.
     *
     * @param retr        the user profile prior to being updated.
     * @param changedUser the user profile being updated.
     */
    private void assertProfileNotExistUsingChangedEmailAddress(final UserProfile retr, final UserProfile changedUser) {
        if (retr.getEmailAddress().equals(changedUser.getEmailAddress())) {
            // user isn't changing the email address
            return;
        }

        changedUser.setUuid(retr.getUuid());
        final List<UserProfile> results = searchByEmail(changedUser);
        if (results.size() > 0) {
            final UserProfile retrByEmailProfile = results.get(0);
            if (!retrByEmailProfile.equals(changedUser)) {
                // user changed email address to an address that already exists
                final MessageData md = new MessageData(UserMessage.U007, changedUser.getEmailAddress());
                throw new DataInputException(md);
            }
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public UserProfile retrieve(Object key) {
        return userRepository.retrieve(UserProfile.class, key);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void delete(final UserProfile data) {
        if (data == null || data.getUuid() == null) {
            return;
        }

        final UserProfile profile = retrieve(data.getUuid());
        if (profile == null) {
            return;
        }

        if (profile.getRole() == Role.ADMIN) {
            final UserSearchCriteriaData criteria = new UserSearchCriteriaData();
            criteria.setRole(Role.ADMIN);
            final List<UserProfile> admins = userRepository.search(criteria);
            if (admins.size() == 1) {
                // can't delete the last admin user
                final MessageData md = new MessageData(UserMessage.U008);
                throw new DataInputException(md);
            }
        }

        userCredentialRepository.delete(UserCredential.class, data.getUuid());
        userRepository.delete(UserProfile.class, data.getUuid());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void changePassword(final String emailAddress, final String newPassword, final String currentPassword) {
        final UserProfile key = new UserProfile();
        key.setEmailAddress(emailAddress);

        final UserProfile profile = SecurityHelper.searchByEmailAddress(key, userRepository);
        if (profile == null) {
            final MessageData message = new MessageData(GeneralMessage.G006, "user profile identified by "
                    + emailAddress);
            throw new DataInputException(message);
        }

        final UserCredential userCredential = profile.getUserCredential();
        userCredential.setPassword(newPassword);

        assertChangePassword(userCredential);

        final String plainTextPassword = userCredential.getPassword();
        final String encryptedPassword = encryptPassword(plainTextPassword);

        final UserCredential retr = userCredentialRepository.retrieve(UserCredential.class, userCredential.getUuid());
        if (retr == null) {
            userCredential.setPassword(encryptedPassword);
            userCredential.setLastModifiedTs(new Timestamp(new Date().getTime()));
            userCredentialRepository.add(userCredential);
        } else {
            SecurityHelper.assertCurrentPassword(retr, currentPassword, passwordValidator);
            retr.setPassword(encryptedPassword);
            retr.setLastModifiedTs(new Timestamp(new Date().getTime()));
            userCredentialRepository.changePassword(retr);
        }
    }

    /**
     * Returns the encrypted representation of the supplied password.
     *
     * @param plainTextPassword the password to encrypt.
     * @return the encrypted password.
     */
    private String encryptPassword(String plainTextPassword) {
        userCredentialValidator.assertValidPassword(plainTextPassword);
        return passwordValidator.encryptPassword(plainTextPassword);
    }

    /**
     * Asserts the supplied password is ready for change.
     *
     * @param userCredential the credentials.
     */
    private void assertChangePassword(final UserCredential userCredential) {
        if (StringUtils.isEmpty(userCredential.getPassword())) {
            final MessageData md = new MessageData(GeneralMessage.G001, "new password");
            throw new DataInputException(md);
        }

        final String uuid = userCredential.getUuid();
        if (uuid == null) {
            final MessageData md = new MessageData(GeneralMessage.G001, "user ID");
            throw new DataInputException(md);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void resetPassword(final String emailAddress, final String resetUuid) {
        if (resetUuid == null) {
            final MessageData md = new MessageData(GeneralMessage.G001, "UUID");
            throw new DataInputException(md);
        }

        LookupKeyValuePK key = new LookupKeyValuePK();
        key.setGroupCode("USER");
        key.setLookupName(resetUuid);
        final LookupKeyValue bean = lookupKeyValueService.retrieve(key);
        if (bean == null) {
            final MessageData md = new MessageData(UserMessage.U006);
            throw new DataInputException(md);
        }

        resetPassword(emailAddress);

        // remove the token
        lookupKeyValueService.delete(bean);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void resetPassword(final String emailAddress) {
        if (StringUtils.isEmpty(emailAddress)) {
            return;
        }

        final UserProfile key = new UserProfile();
        key.setEmailAddress(emailAddress);
        final UserProfile retrUser = SecurityHelper.searchByEmailAddress(key, userRepository);
        if (retrUser == null) {
            final MessageData md = new MessageData(UserMessage.U005, emailAddress);
            throw new DataInputException(md);
        }

        final UserCredential userCredential = retrUser.getUserCredential();
        final String newPassword = passwordGeneratorRepository.generate();

        final String encryptedPassword = passwordValidator.encryptPassword(newPassword);
        userCredential.setPassword(encryptedPassword);
        userCredential.setLastModifiedTs(new Timestamp(new Date().getTime()));
        userCredentialRepository.changePassword(userCredential);

        final String to = retrUser.getEmailAddress();
        final String body = String.format(ConfigConstant.EMAIL_RESET_PASSWORD_BODY, newPassword);
        sendEmail(to, String.format(body, newPassword));
        //
        //        Logger logger = LogFactory.getLogger();
        //        logger.debug("\nPassword Reset Email Contents:\n" + body);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void sendResetConfirmation(final String emailAddress) {
        if (StringUtils.isEmpty(emailAddress)) {
            final MessageData message = new MessageData(GeneralMessage.G001, "email address");
            throw new DataInputException(message);
        }

        final UserProfile key = new UserProfile();
        key.setEmailAddress(emailAddress);
        final UserProfile retr = SecurityHelper.searchByEmailAddress(key, userRepository);
        if (retr == null) {
            final MessageData message = new MessageData(GeneralMessage.G003, "email address");
            throw new DataInputException(message);
        }

        final String lookupUuid = UUID.randomUUID().toString();
        final LookupKeyValue token = new LookupKeyValue("USER", lookupUuid, lookupUuid);
        lookupKeyValueService.add(token);

        final String to = retr.getEmailAddress();
        final String html = getResetHtml(retr, lookupUuid);
        final String body = String.format(ConfigConstant.EMAIL_RESET_PASSWORD_CONFIRMATION_BODY, html);
        sendEmail(to, String.format(body, body));
        //
        //        Logger logger = LogFactory.getLogger();
        //        logger.debug("\nPassword Reset Email Contents:\n" + body);
    }

    /**
     * Sends an email to the supplied email address.
     *
     * @param to   the email address to send the email to.
     * @param body the body of the email.
     */
    private void sendEmail(final String to, final String body) {
        final String from = ConfigConstant.EMAIL_FROM_ADDRESS;
        final String subject = ConfigConstant.EMAIL_RESET_PASSWORD_SUBJECT;
        final String host = ConfigConstant.EMAIL_HOST;

        final Properties props = new Properties();
        props.setProperty(MailRepository.PROP_HOST, host);
        props.setProperty(MailRepository.PROP_FROM, from);
        props.setProperty(MailRepository.PROP_TO, to);
        props.setProperty(MailRepository.PROP_SUBJECT, subject);
        props.setProperty(MailRepository.PROP_BODY, body);

        emailRepository.send(props);
    }

    /**
     * Returns the HTML to allow the user to go to the Reset Password page.
     *
     * @param user the user.
     * @param uuid the UUID which proves the user came from the email to reset the password.
     * @return the HTML.
     */
    private String getResetHtml(final UserProfile user, final String uuid) {
        final String template = "<br><br><a href=\"%s\">CLICK HERE TO RESET YOUR PASSWORD</a>";
        final String url = String.format(ConfigConstant.EMAIL_PASSWORD_RESET_URL, user.getEmailAddress(), uuid);
        return String.format(template, url);
    }

    @Override
    public List<UserProfile> search(final UserSearchCriteriaData criteria) {
        return userRepository.search(criteria);
    }
}
