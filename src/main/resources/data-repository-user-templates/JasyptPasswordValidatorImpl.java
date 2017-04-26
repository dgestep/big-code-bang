package ${topLevelDomain}.${companyName}.${productName}.model.repository.user;

import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.stereotype.Repository;

/**
 * A repository that encrypts passwords using the Jasypt library.
 *
 * @author ${codeAuthor}.
 */
@Repository("JasyptPasswordValidator")
public class JasyptPasswordValidatorImpl implements PasswordValidator {

    @Override
    public String encryptPassword(final String plainTextPassword) {
        final BasicPasswordEncryptor encryptor = new BasicPasswordEncryptor();
        return encryptor.encryptPassword(plainTextPassword);
    }

    @Override
    public boolean isValidPassword(final String plainTextPassword, final String encryptedPassword) {
        final BasicPasswordEncryptor encryptor = new BasicPasswordEncryptor();
        return encryptor.checkPassword(plainTextPassword, encryptedPassword);
    }

    @Override
    public boolean isNotValidPassword(final String plainTextPassword, final String encryptedPassword) {
        return !isValidPassword(plainTextPassword, encryptedPassword);
    }
}
