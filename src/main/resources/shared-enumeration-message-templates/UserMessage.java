package ${topLevelDomain}.${companyName}.${productName}.model.enumeration.message;

/**
 * Contains messages specific to the user module.
 *
 * @author ${codeAuthor}.
 */
public enum UserMessage implements ServiceMessage {
    /**
     * The supplied password is incorrect
     */
    U001("The supplied password is incorrect"),
    /**
     * Please supply your email address prior to logging in
     */
    U002("Please supply your email address prior to logging in"),
    /**
     * The user is inactive
     */
    U003("The user is inactive"),
    /**
     * The password must be at least 8 characters in length, have at least one capital letter and at least one
     * number
     */
    U004("The password must be at least 8 characters in length, have at least one capital "
            + "letter and at least one number"),
    /**
     * No user profile exists in our system under %s
     */
    U005("No user profile exists in our system under %s"),
    /**
     * You are not permitted to reset the password.
     */
    U006("You are not permitted to reset the password."),
    /**
     * A user already exists for the supplied email address. ( %s )
     */
    U007("A user already exists for the supplied email address. ( %s )"),
    /**
     * There needs to be at least on administrative user in the system.
     */
    U008("There needs to be at least on administrative user in the system.");

    private String message;

    /**
     * Constructs this enumeration.
     *
     * @param message sets the message.
     */
    UserMessage(final String message) {
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
