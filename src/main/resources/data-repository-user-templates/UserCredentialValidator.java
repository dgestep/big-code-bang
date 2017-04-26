package ${topLevelDomain}.${companyName}.${productName}.model.repository.user;

/**
 * Validates a users' credentials.
 *
 * @author ${codeAuthor}.
 */
public interface UserCredentialValidator {

    /**
     * Asserts that the supplied password passes the validation rules.
     *
     * @param password the password.
     */
    void assertValidPassword(String password);
}
