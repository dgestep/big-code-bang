package com.${companyName}.${productName}.model.service.user;

import com.${companyName}.${productName}.model.criteria.UserSearchCriteriaData;
import com.${companyName}.${productName}.model.data.UserProfile;
import com.${companyName}.${productName}.model.service.CrudService;

import java.util.List;

/**
 * Defines a class which saves, deletes, and retrieve user profile information.
 *
 * @author ${codeAuthor}.
 */
public interface UserService extends CrudService<UserProfile> {

    /**
     * Updates the user password with the supplied password on the credential.
     *
     * @param emailAddress    the email address.
     * @param newPassword     the new password.
     * @param currentPassword the current plain text password. Supply null if adding a user credential.
     */
    void changePassword(String emailAddress, String newPassword, String currentPassword);

    /**
     * Sends an email confirmation with regards to resetting the password to the supplied user.
     *
     * @param emailAddress the email address.
     */
    void sendResetConfirmation(String emailAddress);

    /**
     * Resets the password for the supplied user.
     *
     * @param emailAddress the email address.
     */
    void resetPassword(String emailAddress);

    /**
     * Resets the password for the supplied user.
     *
     * @param emailAddress the email address.
     * @param resetUuid    used for lookup verification.
     */
    void resetPassword(String emailAddress, String resetUuid);

    /**
     * Return a list of user profiles that match the supplied criteria.
     *
     * @param criteria the search criteria.
     * @return the list or empty list if not found.
     */
    List<UserProfile> search(UserSearchCriteriaData criteria);
}
