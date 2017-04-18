package com.${companyName}.${productName}.model.service;

import com.${companyName}.${productName}.model.data.MessageData;
import com.${companyName}.${productName}.model.enumeration.message.GeneralMessage;
import com.${companyName}.${productName}.model.exception.DataInputException;
import com.${companyName}.${productName}.model.exception.SystemLoggedException;
import com.${companyName}.${productName}.model.repository.DataSet;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.OneToOne;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Accepts an entity class and asserts that the supplied values are valid.
 *
 * @author ${codeAuthor}.
 */
public class EntityAssertion {
    private static final int DEFAULT_STRING_LENGTH = 255;
    private static final Set<Class<?>> BASE_TYPES = DataSet.getBaseTypes();
    private static final Set<Class<?>> BOTTOM_TYPES = DataSet.getBottomTypes();
    private List<MessageData> messages = new ArrayList<>();

    /**
     * Examines the supplied {@link javax.persistence.Entity} class and validates the property values assigned to the
     * supplied instance.
     *
     * @param data the data to evaluate.
     */
    public void evaluate(final Object data) {
        if (data == null) {
            return;
        }

        final Class<?> dataClz = data.getClass();
        try {
            messages = new ArrayList<>();

            doVerifications(data, dataClz);
        }
        catch (final SystemLoggedException sle) {
            throw sle;
        }
        catch (final Exception exception) {
            throw new SystemLoggedException(exception);
        }

        if (messages.size() > 0) {
            // one or more properties failed an assertion
            throw new DataInputException(messages);
        }
    }

    /**
     * Performs the analysis on the supplied object.
     *
     * @param data    the object to verify.
     * @param dataClz the class of the object.
     * @throws Exception thrown for all exceptions.
     */
    private void doVerifications(final Object data, final Class<?> dataClz) throws Exception {
        if (BOTTOM_TYPES.contains(dataClz)) {
            return;
        }

        // populate properties of all super classes first
        final Class<?> superDataClz = dataClz.getSuperclass();
        doVerifications(data, superDataClz);

        // populate properties of the supplied data instance
        final Field[] fields = dataClz.getDeclaredFields();
        for (final Field field : fields) {
            field.setAccessible(true);

            final int modifiers = field.getModifiers();
            if (Modifier.isStatic(modifiers)) {
                continue;
            }

            final Class<?> fieldType = field.getType();
            if (fieldType.isPrimitive() || BASE_TYPES.contains(fieldType)) {
                doVerificationFromAnnotation(data, field);
            } else {
                doVerificationFromNestedProperties(data, field);
            }
        }
    }

    /**
     * Verifies the data based on the specification from the annotation.
     *
     * @param data  the data to verify.
     * @param field the field.
     * @throws DataInputException     thrown if the verification failed.
     * @throws IllegalAccessException thrown if the object cannot be accessed.
     */
    private void doVerificationFromAnnotation(final Object data, final Field field)
            throws DataInputException, IllegalAccessException {
        final Column colAnnotation = field.getDeclaredAnnotation(Column.class);
        if (colAnnotation == null) {
            return;
        }

        final String dbColumnName = colAnnotation.name();
        final Object value = field.get(data);
        final boolean notAllowNulls = !colAnnotation.nullable();
        final Class<?> fieldType = field.getType();
        final boolean notPrimitiveType = !fieldType.isPrimitive();

        if (notAllowNulls && notPrimitiveType && value == null) {
            MessageData messageData = new MessageData(GeneralMessage.G001, dbColumnName);
            messageData.setColumnName(dbColumnName);
            messages.add(messageData);
            return;
        }

        if (value != null && fieldType.isAssignableFrom(String.class)) {
            final String str = (String) value;
            if (notAllowNulls && str.length() == 0) {
                MessageData messageData = new MessageData(GeneralMessage.G001, dbColumnName);
                messageData.setColumnName(dbColumnName);
                messages.add(messageData);
            } else {
                // it is assumed no length attribute was supplied on the Column annotation
                // if the length is the default string length
                final int maximum = colAnnotation.length();
                if (maximum != DEFAULT_STRING_LENGTH) {
                    final int actual = str.length();
                    if (actual > maximum) {
                        MessageData messageData = new MessageData(GeneralMessage.G005, dbColumnName, actual,
                                maximum);
                        messageData.setColumnName(dbColumnName);
                        messages.add(messageData);
                    }
                }
            }
        }
    }

    /**
     * Verifies the data based on the specification obtained from nested objects.
     *
     * @param data  the data to verify.
     * @param field the field.
     * @throws Exception thrown for all exceptions.
     */
    private void doVerificationFromNestedProperties(final Object data, final Field field) throws Exception {
        final EmbeddedId embeddedId = field.getDeclaredAnnotation(EmbeddedId.class);
        final OneToOne oneAnnotation = field.getDeclaredAnnotation(OneToOne.class);
        if (embeddedId == null && oneAnnotation == null) {
            return;
        }

        final Object objValue = field.get(data);
        if (objValue != null) {
            doVerifications(objValue, objValue.getClass());
        }
    }
}
