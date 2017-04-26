package ${topLevelDomain}.${companyName}.${productName}.model.repository.user;

/**
 * Defines a class which generates a password.
 *
 * @author ${codeAuthor}.
 */
public interface PasswordGeneratorRepository {
    /**
     * Returns a generated password.
     *
     * @return the generated password.
     */
    String generate();
}
