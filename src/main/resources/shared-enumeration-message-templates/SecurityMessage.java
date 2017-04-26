package ${topLevelDomain}.${companyName}.${productName}.model.enumeration.message;

/**
 * Contains messages specific to the security module.
 *
 * @author ${codeAuthor}.
 */
public enum SecurityMessage implements ServiceMessage {
    /**
     * Failed to authenticate user with provided credentials
     */
    SC001("Failed to authenticate user with provided credentials"),
    /**
     * User token is corrupt or no longer valid. UUID: %s - Token: %s
     */
    SC002("User token is corrupt or no longer valid. UUID: %s - Token: %s"),
    /**
     * The requested resource is restricted to authorized personnel only
     */
    SC003("The requested resource is restricted to authorized personnel only"),
    /**
     * You are not authorized to access the requested resource
     */
    SC004("You are not authorized to access the requested resource"),
    /**
     * The user identified by %s is not found
     */
    SC005("The user identified by %s is not found");

    private String message;

    /**
     * Creates an instance of this enumeration.
     *
     * @param message the message to apply.
     */
    SecurityMessage(final String message) { // NOCHECKSTYLE
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getCode() {
        return this.toString();
    }
}
