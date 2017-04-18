package com.${companyName}.${productName}.model.exception;

import com.${companyName}.${productName}.model.data.MessageData;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * An exception representing invalid or missing data.
 *
 * @author ${codeAuthor}.
 */
public class DataInputException extends RuntimeException {
    private static final long serialVersionUID = 8949236165484886595L;

    private List<MessageData> messageData;

    /**
     * Creates an instance of this exception.
     */
    public DataInputException() {
        super();
    }

    /**
     * Creates an instance of this exception.
     *
     * @param message the message.
     */
    public DataInputException(final String message) {
        super(message);
    }

    /**
     * Creates an instance of this exception.
     *
     * @param message the message.
     * @param thr     the {@link Throwable} associated with this exception.
     */
    public DataInputException(final String message, final Throwable thr) {
        super(message, thr);
    }

    /**
     * Creates an instance of this exception.
     *
     * @param thr the {@link Throwable} associated with this exception.
     */
    public DataInputException(final Throwable thr) {
        super(thr);
    }

    /**
     * Creates an instance of this exception.
     *
     * @param message   the message.
     * @param errorCode the code associated with this exception.
     */
    public DataInputException(final String message, final String errorCode) {
        super(message);

        final MessageData md = new MessageData(message, errorCode);
        addMessage(md);
    }

    /**
     * Creates an instance of this exception.
     *
     * @param message    the message.
     * @param errorCode  the code associated with this exception.
     * @param columnName the UI column name to bind this message to.
     */
    public DataInputException(final String message, final String errorCode, final String columnName) {
        super(message);

        final MessageData md = new MessageData(message, errorCode, columnName);
        addMessage(md);
    }

    /**
     * Constructs and assigns the message data to this exception.
     *
     * @param md the message data to assign to this exception.
     */
    public DataInputException(final MessageData md) {
        addMessage(md);
    }

    /**
     * Constructs and assigns the supplied list of message data elements to this exception.
     *
     * @param messageData the list of message data elements.
     */
    public DataInputException(final List<MessageData> messageData) { //NOCHECKSTYLE
        this.messageData = messageData;
    }

    /**
     * Adds the supplied message to the list of messages associated to this exception.
     *
     * @param message the message to add.
     */
    public void addMessage(final MessageData message) {
        if (messageData == null) {
            messageData = new ArrayList<>();
        }
        messageData.add(message);
    }

    /**
     * Returns the first message added to this exception.
     *
     * @return Returns the first message.
     */
    @Override
    public String getMessage() {
        if (messageData == null) {
            return super.getMessage();
        }

        final String lineSeparator = System.getProperty("line.separator");
        return getMessagesAsString(lineSeparator);
    }

    /**
     * Returns the first {@link MessageData} element associated with this exception.
     *
     * @return the message.
     */
    public MessageData getFirstMessageData() {
        if (messageData == null) {
            return null;
        }

        return messageData.get(0);
    }

    /**
     * Returns the message within the list of messages associated to the supplied error code.
     *
     * @param errorCode identifies the message to retrieve.
     * @return the message or null if not found.
     */
    public MessageData getMessageData(final String errorCode) {
        final List<MessageData> messages = getMessageData();
        if (CollectionUtils.isEmpty(messages)) {
            return null;
        }

        final MessageData key = new MessageData(errorCode);
        final int idx = messages.indexOf(key);
        if (idx < 0) {
            return null;
        }

        return messages.get(idx);
    }

    /**
     * Assigns the supplied column name to the appropriate message within the list of messages on this
     * exception.
     *
     * @param errorCode  identifies which message to assign the column name to.
     * @param columnName the column name to assign.
     */
    public void setColumnNameToMessageData(final String errorCode, final String columnName) {
        final MessageData md = getMessageData(errorCode);
        if (md != null) {
            md.setColumnName(columnName);
        }
    }

    /**
     * Returns the list of {@link MessageData} elements associated with this exception.
     *
     * @return the list of messages.
     */
    public List<MessageData> getMessageData() {
        return messageData;
    }

    /**
     * Returns all messages added to this exception as a String.
     *
     * @param lineSeparator the value to place between each message.
     * @return the messages.
     */
    public String getMessagesAsString(final String lineSeparator) {
        if (messageData == null) {
            return super.getMessage();
        }

        final StringBuilder buf = new StringBuilder();
        for (final MessageData md : messageData) {
            if (buf.length() > 0) {
                buf.append(lineSeparator);
            }
            buf.append(md.getMessage());
        }
        return buf.toString();
    }
}
