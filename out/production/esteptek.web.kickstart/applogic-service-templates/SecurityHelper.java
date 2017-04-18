package com.${companyName}.${productName}.model.service;

import com.${companyName}.${productName}.model.criteria.UserSearchCriteriaData;
import com.${companyName}.${productName}.model.data.MessageData;
import com.${companyName}.${productName}.model.data.UserCredential;
import com.${companyName}.${productName}.model.data.UserProfile;
import com.${companyName}.${productName}.model.enumeration.message.UserMessage;
import com.${companyName}.${productName}.model.exception.DataInputException;
import com.${companyName}.${productName}.model.repository.user.PasswordValidator;
import com.${companyName}.${productName}.model.repository.user.UserRepository;

import java.util.List;

/**
 * Contains static helper methods regarding security methods.
 *
 * @author ${codeAuthor}.
 */
public final class SecurityHelper {

    /**
     * Creates an instance of this class.
     */
    private SecurityHelper() {
    }

    /**
     * Searches the database by email address.
     *
     * @param user           the user.
     * @param userRepository the user repository.
     * @return the found user.
     */
    public static UserProfile searchByEmailAddress(final UserProfile user, final UserRepository userRepository) {
        UserSearchCriteriaData criteria = new UserSearchCriteriaData();
        criteria.setEmailAddress(user.getEmailAddress());
        List<UserProfile> profiles = userRepository.search(criteria);

        final UserProfile profile;
        if (profiles.isEmpty()) {
            profile = null;
        } else {
            profile = profiles.get(0);
        }

        return profile;
    }

    /**
     * Validates against the saved password.
     *
     * @param storedCredential  the stored credentials.
     * @param plainTextPassword the supplied password.
     * @param passwordValidator the password validator.
     */
    public static void assertCurrentPassword(final UserCredential storedCredential, final String plainTextPassword,
            final PasswordValidator passwordValidator) {
        final String encryptedPassword = storedCredential.getPassword();

        if (passwordValidator.isNotValidPassword(plainTextPassword, encryptedPassword)) {
            // bad password
            throw new DataInputException(new MessageData(UserMessage.U001));
        }
    }

}
