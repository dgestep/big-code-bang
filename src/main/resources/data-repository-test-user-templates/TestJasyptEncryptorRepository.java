package ${topLevelDomain}.${companyName}.${productName}.model.repository.user;

import org.junit.Assert;
import junit.framework.TestCase;

public class TestJasyptEncryptorRepository extends TestCase {
    private final JasyptPasswordValidatorImpl passwordValidator;

    public TestJasyptEncryptorRepository() {
        passwordValidator = new JasyptPasswordValidatorImpl();
    }

    public void testEncryptDecrypt() {
        final String plainTextPassword = "battlestar galactica";
        try {
            final String encryptedPassword = passwordValidator.encryptPassword(plainTextPassword);
            System.out.println(encryptedPassword);

            Assert.assertTrue(passwordValidator.isValidPassword(plainTextPassword, encryptedPassword));

            Assert.assertFalse(passwordValidator.isNotValidPassword(plainTextPassword, encryptedPassword));
        } catch (final Throwable t) {
            t.printStackTrace();
            fail(t.toString());
        }
    }
}
