package ${topLevelDomain}.${companyName}.${productName}.model.repository.user;

import ${topLevelDomain}.${companyName}.${productName}.model.data.UserCredential;
import ${topLevelDomain}.${companyName}.${productName}.model.repository.CrudRepository;

/**
 * Allows clients to add, update, delete, and retrieve user credential information from the data repository.
 *
 * @author ${codeAuthor}.
 */
public interface UserCredentialRepository extends CrudRepository<UserCredential> {
    /**
     * Updates the user password related to the supplied user.
     *
     * @param userCredential the user and their credentials.
     */
    void changePassword(UserCredential userCredential);
}
