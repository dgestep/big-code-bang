package com.${companyName}.${productName}.model.data;

import com.${companyName}.${productName}.model.enumeration.message.ServiceMessage;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Represents the elements that make up a message.
 *
 * @author ${codeAuthor}.
 */
@SuppressWarnings("PMD")
// CHECKSTYLE:OFF
public class MessageData implements Serializable {
    private static final long serialVersionUID = 2098820535644196171L;
    private String code;
    private String columnName;
    private String message;

    /**
     * Creates an instance of this class.
     */
    public MessageData() {
    }

    /**
     * Creates an instance of this class.
     *
     * @param code the code.
     */
    public MessageData(final String code) {
        this();
        setCode(code);
    }

    /**
     * Creates an instance of this class.
     *
     * @param message the message.
     * @param code    the code.
     */
    public MessageData(final String message, final String code) {
        this(code);
        setMessage(message);
    }

    /**
     * Creates an instance of this class.
     *
     * @param serviceMessage the {@link ServiceMessage} to apply as the message.
     * @param args           any substitution arguments to apply to the message associated with the supplied {@link
     *                       ServiceMessage}.
     */
    public MessageData(final ServiceMessage serviceMessage, final Object... args) {
        this();

        final String code;
        final String message;
        if (serviceMessage != null) {
            if (args != null && args.length > 0) {
                message = String.format(serviceMessage.getMessage(), args);
            } else {
                message = serviceMessage.getMessage();
            }
            code = serviceMessage.getCode();
        } else {
            message = StringUtils.EMPTY;
            code = StringUtils.EMPTY;
        }
        setCode(code);
        setMessage(message);
    }

    /**
     * Creates an instance of this class.
     *
     * @param message    the message.
     * @param code       the code.
     * @param columnName the column name.
     */
    public MessageData(final String message, final String code, final String columnName) {
        this(message, code);
        setColumnName(columnName);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof MessageData)) return false;

        MessageData that = (MessageData) o;

        return new EqualsBuilder().append(code, that.code).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(code).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("code", code).append("columnName", columnName)
                .append("message", message).toString();
    }
}
//NOCHECKSTYLE:ON