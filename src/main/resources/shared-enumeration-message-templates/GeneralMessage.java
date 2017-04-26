package ${topLevelDomain}.${companyName}.${productName}.model.enumeration.message;

/**
 * Contains messages not specific to any particular module.
 *
 * @author ${codeAuthor}.
 */
public enum GeneralMessage implements ServiceMessage {
    /**
     * A value is required for %s
     */
    G001("A value is required for %s."),
    /**
     * Missing %s information
     */
    G002("Missing %s information."),
    /**
     * The %s is not found
     */
    G003("The %s is not found."),
    /**
     * An unexpected error has occurred. The cause has been logged on the server for troubleshooting purposes. We
     * apologize for any inconvenience this has caused you.
     */
    G004("An unexpected error has occurred. The cause has been logged on the server for troubleshooting purposes. "
            + "We apologize for any inconvenience this has caused you."),
    /**
     * Value too large for column %s (actual: %s, maximum: %s)
     */
    G005("Value too large for column %s (actual: %s, maximum: %s)."),
    /**
     * The %s information is no longer available
     */
    G006("The %s information is no longer available."),
    /**
     * Success.
     */
    G007("Success."),
    /**
     * The %s information identified by %s is not found
     */
    G008("The %s information identified by %s is not found."),
    /**
     * No results found.
     */
    G009("No results found."),
    /**
     * This transaction will result in a duplicate record. %s
     */
    G010("This transaction will result in a duplicate record. %s"),
    /**
     * This transaction is missing dependent information. %s
     */
    G011("This transaction is missing dependent information. %s"),
    /**
     * The data being acted upon has related information that must be handled prior to issuing this transaction. %s
     */
    G012("The data being acted upon has related information that "
            + "must be handled prior to issuing this transaction. %s"),
    /**
     * %s cannot be converted to a number
     */
    G013("%s cannot be converted to a number.");

    private String message;

    /**
     * Creates an instance of this enumeration.
     *
     * @param message the message to apply.
     */
    GeneralMessage(final String message) { // NOCHECKSTYLE
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
