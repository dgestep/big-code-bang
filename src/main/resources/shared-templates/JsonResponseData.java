package ${topLevelDomain}.${companyName}.${productName}.model;

import com.google.gson.ExclusionStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ${topLevelDomain}.${companyName}.${productName}.model.data.MessageData;
import ${topLevelDomain}.${companyName}.${productName}.model.enumeration.message.ServiceMessage;
import ${topLevelDomain}.${companyName}.${productName}.model.exception.SystemLoggedException;
import ${topLevelDomain}.${companyName}.${productName}.model.log.LogFactory;
import ${topLevelDomain}.${companyName}.${productName}.model.log.Logger;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Container for a JSON response.
 *
 * @author ${codeAuthor}.
 */
public class JsonResponseData {
    private final int statusCode;
    private final List<MessageData> messages;
    private final Object data;

    /**
     * Creates an instance of this class.
     *
     * @param statusCode the response statusCode to apply.
     * @param message    the message to apply.
     */
    public JsonResponseData(final int statusCode, final String message) { //NOCHECKSTYLE
        this(statusCode, message, null);
    }

    /**
     * Creates an instance of this class.
     *
     * @param statusCode the response statusCode to apply.
     * @param message    the message to apply.
     * @param data       any additional data to return. Can be null.
     */
    public JsonResponseData(final int statusCode, final String message, final Object data) { //NOCHECKSTYLE
        this.statusCode = statusCode;
        messages = new ArrayList<>();
        if (StringUtils.isNotEmpty(message)) {
            messages.add(new MessageData(message, String.valueOf(statusCode)));
        }
        this.data = data;
    }

    /**
     * Creates an instance of this class.
     *
     * @param statusCode the response statusCode to apply.
     * @param message    the message to apply.
     */
    public JsonResponseData(final int statusCode, final MessageData message) { //NOCHECKSTYLE
        final List<MessageData> messages = new ArrayList<>(); //NOCHECKSTYLE
        messages.add(message);

        this.statusCode = statusCode;
        this.messages = messages;
        this.data = null;
    }

    /**
     * Creates an instance of this class.
     *
     * @param statusCode the response statusCode to apply.
     * @param messages   the messages to apply.
     */
    public JsonResponseData(final int statusCode, final List<MessageData> messages) { //NOCHECKSTYLE
        this(statusCode, messages, null);
    }

    /**
     * Creates an instance of this class.
     *
     * @param statusCode the response statusCode to apply.
     * @param messages   the messages to apply.
     * @param data       any additional data to return. Can be null.
     */
    public JsonResponseData(final int statusCode, final List<MessageData> messages, final Object data) { //NOCHECKSTYLE
        this.statusCode = statusCode;
        this.messages = messages;
        this.data = data;
    }

    /**
     * Creates an instance of this class.
     *
     * @param statusCode the response statusCode to apply.
     * @param message    the message to apply.
     */
    public JsonResponseData(final int statusCode, final ServiceMessage message) { //NOCHECKSTYLE
        this(statusCode, message, null);
    }

    /**
     * Creates an instance of this class.
     *
     * @param statusCode the response statusCode to apply.
     * @param message    the message to apply.
     * @param data       any additional data to return. Can be null.
     */
    public JsonResponseData(final int statusCode, final ServiceMessage message, final Object data) { //NOCHECKSTYLE
        this.statusCode = statusCode;
        this.data = data;
        messages = new ArrayList<>();
        if (message != null) {
            final MessageData messageData = new MessageData(message, String.valueOf(statusCode));
            messages.add(messageData);
        }
    }

    /**
     * Converts the supplied response object to a JSON message.
     *
     * @param response   the response to convert to JSON.
     * @param exclusions 0 to many GSON exclusion patterns to use to build the JSON response.
     * @return the JSON string.
     */
    public static String toJson(final JsonResponseData response, final ExclusionStrategy... exclusions) {
        Gson gson;
        try {
            if (ArrayUtils.isEmpty(exclusions)) {
                gson = new Gson();
            } else {
                gson = new GsonBuilder().setExclusionStrategies(exclusions).create();
            }
            return gson.toJson(response);
        }
        catch (final Throwable throwable) {
            final Logger logger = LogFactory.getLogger();
            logger.error(throwable.getMessage(), throwable);
            throw new SystemLoggedException(throwable);
        }
    }

    /**
     * Returns the status code set to this response.
     *
     * @return the code.
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Returns the list of messages set to this response.
     *
     * @return the messages.
     */
    public List<MessageData> getMessages() {
        return messages;
    }

    /**
     * Returns the data object set to this response.
     *
     * @return the data.
     */
    public Object getData() {
        return data;
    }

    // CHECKSTYLE:OFF
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof JsonResponseData)) return false;

        JsonResponseData that = (JsonResponseData) o;

        return new EqualsBuilder().append(statusCode, that.statusCode).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(statusCode).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("statusCode", statusCode).append("messages", messages)
                .append("data", data).toString();
    }
    // CHECKSTYLE:ON
}
