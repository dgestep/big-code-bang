package ${topLevelDomain}.${companyName}.${productName}.model.enumeration.message;

/**
 * Represents any message.
 *
 * @author ${codeAuthor}.
 */
public interface ServiceMessage {
    /**
     * Returns a code which uniquely identifies the message.
     *
     * @return the code.
     */
    String getCode();

    /**
     * Returns the message.
     *
     * @return the message.
     */
    String getMessage();
}
