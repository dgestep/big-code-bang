package ${topLevelDomain}.${companyName}.${productName}.model.repository.user;

import ${topLevelDomain}.${companyName}.${productName}.model.data.MessageData;
import ${topLevelDomain}.${companyName}.${productName}.model.enumeration.message.UserMessage;
import ${topLevelDomain}.${companyName}.${productName}.model.exception.DataInputException;
import org.springframework.stereotype.Repository;

/**
 * Validates a users' credentials.
 *
 * @author ${codeAuthor}.
 */
@Repository("UserCredentialValidator")
public class UserCredentialValidatorImpl implements UserCredentialValidator {
    /**
     * Minimum number of characters a password must have.
     */
    private static final int MIN_PASS_CHARS = 8;
    /**
     * Ascii 0.
     */
    private static final int ASCII_ZERO = 48;
    /**
     * Ascii 9.
     */
    private static final int ASCII_NINE = 57;

    /**
     * Ascii A.
     */
    private static final int ASCII_A = 65;
    /**
     * Ascii Z.
     */
    private static final int ASCII_Z = 90;

    @Override
    public void assertValidPassword(final String password) {
        if (password.length() < MIN_PASS_CHARS) {
            // invalid length
            final MessageData md = new MessageData(UserMessage.U004);
            throw new DataInputException(md);
        }
        if (isNotContainsNumber(password)) {
            // missing one numeric character
            final MessageData md = new MessageData(UserMessage.U004);
            throw new DataInputException(md);
        }
        if (isNotContainsCapitalLetter(password)) {
            // missing one numeric character
            final MessageData md = new MessageData(UserMessage.U004);
            throw new DataInputException(md);
        }
    }

    /**
     * Returns true if the supplied password does NOT contain a number.
     *
     * @param password the password.
     * @return true if no number.
     */
    private boolean isNotContainsNumber(final String password) {
        boolean containsNumber = false;
        for (int i = 0, n = password.length(); i < n; i++) {
            final int ch = password.charAt(i);
            if (ch >= ASCII_ZERO && ch <= ASCII_NINE) {
                containsNumber = true;
                break;
            }
        }
        return !containsNumber;
    }

    /**
     * Returns true if the supplied password does NOT contain an uppercase letter.
     *
     * @param password the password.
     * @return true if no uppercase letter.
     */
    private boolean isNotContainsCapitalLetter(final String password) {
        boolean containsCapital = false;
        for (int i = 0, n = password.length(); i < n; i++) {
            final int ch = password.charAt(i);
            if (ch >= ASCII_A && ch <= ASCII_Z) {
                containsCapital = true;
                break;
            }
        }
        return !containsCapital;
    }
}
