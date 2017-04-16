package com.${companyName}.${productName}.model.repository;

import com.${companyName}.${productName}.model.exception.SystemLoggedException;
import com.${companyName}.${productName}.model.log.LogFactory;
import com.${companyName}.${productName}.model.log.Logger;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.EntityManager;
import javax.persistence.OneToOne;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * Creates an instance of a class which contains properties that are annotated by the javax.persistence.* annotations
 * and applies the data to the properties using the supplied {@link ResultSet}.
 * <p>
 * <p>
 * The population of the properties takes into consideration nested properties, meaning objects that themselves
 * contain properties that are annotated by the javax.persistence.* annotations are also evaluated.
 * </p>
 * <p>
 * <p>
 * Inheritance is also supported, meaning super classes that contain properties that are annotated by the
 * javax.persistence.* annotations are also evaluated.
 * </p>
 * <br>
 * Example 1:<br>
 * <code><br>
 * // Creates an instance of Employee and populates all properties. <br>
 * DataSet dataSet = new DataSet();<br>
 * Employee employee = dataSet.instanceOf(Employee.class, resultSet);<br>
 * </code>
 * <br><br>
 * Example 2:<br>
 * <code>public class Manager extends Employee { ... }</code><br><br>
 * // Creates an instance of Manager and populates both Manager and Employees properties.<br>
 * DataSet dataSet = new DataSet();<br>
 * Manager manager = dataSet.instanceOf(Manager.class, resultSet);<br>
 *
 * @author ${codeAuthor}.
 */
public class DataSet { // NOPMD excessive class length
    private static final String FINAL_FIELD_MSG =
            "Class: %s, Property: %s, Cannot apply value because the field is declared final and already has a value";
    private static final Set<Class<?>> BASE_TYPES = getBaseTypes();
    private static final Set<Class<?>> BOTTOM_TYPES = getBottomTypes();
    private ResultSetManager resultSetManager;
    private Logger logger = LogFactory.getLogger();

    /**
     * Creates an instance of this class.
     */
    public DataSet() {
    }

    /**
     * Creates an instance of this class.
     *
     * @param resultSetManager sets the {@link ResultSetManager} dependency.
     */
    public DataSet(final ResultSetManager resultSetManager) { //NOCHECKSTYLE
        this.resultSetManager = resultSetManager;
    }

    /**
     * Returns a Set of class types representing the bottom layer of types to drill into.
     *
     * @return the types.
     */
    public static Set<Class<?>> getBaseTypes() {
        final Set<Class<?>> types = new HashSet<>();
        types.add(Boolean.class);
        types.add(Character.class);
        types.add(Byte.class);
        types.add(Short.class);
        types.add(Integer.class);
        types.add(Long.class);
        types.add(Float.class);
        types.add(Double.class);
        types.add(BigDecimal.class);
        types.add(BigInteger.class);
        types.add(Byte.class);

        types.add(String.class);
        types.add(Date.class);
        types.add(java.sql.Date.class);
        types.add(Timestamp.class);
        types.add(Time.class);

        return types;
    }

    /**
     * Returns a Set of class types indicating the logic has hit the bottom of the inheritance stack.
     *
     * @return the types.
     */
    public static Set<Class<?>> getBottomTypes() {
        final Set<Class<?>> types = new HashSet<>();
        types.add(Object.class);
        types.add(Class.class);

        return types;
    }

    /**
     * Returns the supplied column name with the intent of being included in an SQL SELECT statement, associating the
     * supplied alias to the column.
     *
     * @param alias              the alias to associate to the database column name. Can be null or empty string.
     * @param databaseColumnName the column name from the database table.
     * @return the combined alias/database column name suitable for an SQL SELECT statement.  Example: alias="pnt",
     * columnName="partNumber" : return value would be "pnt.partNumber as pnt_partNumber".  Returns the supplied column
     * name if alias is empty or null.
     */
    @SuppressWarnings("PMD.UseStringBufferForStringAppends")
    public static String col(final String alias, final String databaseColumnName) {
        String sql;
        if (StringUtils.isNotEmpty(alias)) {
            sql = alias + "." + databaseColumnName;
        } else {
            sql = databaseColumnName;
        }
        if (StringUtils.isNotEmpty(alias)) {
            sql += " as " + alias + "_" + databaseColumnName;
        }
        return sql;
    }

    /**
     * Creates an instance of the supplied class and sets the property values using the data contained within the {@link
     * ResultSet}.
     *
     * @param dataClz   the class to populate.
     * @param resultSet the data to populate the class with.
     * @param <R>       the type being returned from this method.
     * @return the instance populated from the supplied {@link ResultSet}. Returns null if no data existed in the {@link
     * ResultSet} which could be applied to the supplied class.
     */
    public <R> R instanceOf(final Class<?> dataClz, final ResultSet resultSet) {
        return instanceOf(dataClz, resultSet, null, null);
    }

    /**
     * Creates an instance of the supplied class and sets the property values using the data contained within the {@link
     * ResultSet}.
     *
     * @param dataClz       the class to populate.
     * @param resultSet     the data to populate the class with.
     * @param entityManager if supplied, the entity manager will be invoke to merge the state of the created instance
     *                      into the current persistent context.
     * @param <R>           the type being returned from this method.
     * @return the instance populated from the supplied {@link ResultSet}. Returns null if no data existed in the {@link
     * ResultSet} which could be applied to the supplied class.
     */
    public <R> R instanceOf(final Class<?> dataClz, final ResultSet resultSet,
            final EntityManager entityManager) {
        return instanceOf(dataClz, resultSet, null, entityManager);
    }

    /**
     * Creates an instance of the supplied class and sets the property values using the data contained within the {@link
     * ResultSet}.
     *
     * @param dataClz   the class to populate.
     * @param resultSet the data to populate the class with.
     * @param aliases   a {@link Map} containing the property name and an alias to use for the database column that
     *                  relates to the property.  If the property name represents a non-primitive Object, then every
     *                  property in that object will use the associated alias. Supply null to associate no aliases.
     * @param <R>       the type being returned from this method.
     * @return the instance populated from the supplied {@link ResultSet}. Returns null if no data existed in the {@link
     * ResultSet} which could be applied to the supplied class.
     */
    public <R> R instanceOf(final Class<?> dataClz, final ResultSet resultSet,
            final Map<String, String> aliases) {
        return instanceOf(dataClz, resultSet, aliases, null);
    }

    /**
     * Creates an instance of the supplied class and sets the property values using the data contained within the {@link
     * ResultSet}.
     *
     * @param dataClz       the class to populate.
     * @param resultSet     the data to populate the class with.
     * @param aliases       a {@link Map} containing the property name and an alias to use for the database column that
     *                      relates to the property.  If the property name represents a non-primitive Object, then every
     *                      property in that object will use the associated alias. Supply null to associate no aliases.
     * @param entityManager if supplied, the entity manager will be invoke to merge the state of the created instance
     *                      into the current persistent context.
     * @param <R>           the type being returned from this method.
     * @return the instance populated from the supplied {@link ResultSet}. Returns null if no data existed in the {@link
     * ResultSet} which could be applied to the supplied class.
     */
    public <R> R instanceOf(final Class<?> dataClz, final ResultSet resultSet,
            final Map<String, String> aliases, final EntityManager entityManager) {
        if (resultSetManager == null) {
            resultSetManager = new ResultSetManager(resultSet);
        }

        final R data = createInstance(dataClz, resultSet, aliases, null);
        return entityManager == null || data == null ? data : entityManager.merge(data);
    }

    /**
     * Creates an instance of the supplied class and sets the values of all the properties on the data
     * class with their counterpart from the supplied {@link ResultSet}.
     *
     * @param dataClz      the class to populate.
     * @param resultSet    contains the data.
     * @param aliases      a {@link Map} containing the property name and an alias to use for the database column that
     *                     relates to the property.  If the property name represents a non-primitive Object, then every
     *                     property in that object will use the associated alias. Supply null to associate no aliases.
     * @param appliedAlias the alias currently in use.  Set to null to not signal an alias in use.
     * @param <R>          the type being returned from this method.
     * @return the instance populated from the supplied {@link ResultSet}.
     */
    @SuppressWarnings("unchecked")
    private <R> R createInstance(final Class<?> dataClz, final ResultSet resultSet,
            final Map<String, String> aliases, String appliedAlias) {
        if (dataClz == null || resultSet == null) {
            return null;
        }

        try {
            // used as a flag to signal if at least one property of the class was set
            final Set<Short> dataPopulated = new HashSet<>();

            final Object data = dataClz.newInstance();
            applyValues(data, dataClz, resultSet, aliases, appliedAlias, dataPopulated);

            return dataPopulated.size() == 1 ? (R) data : null;
        }
        catch (final SystemLoggedException sle) {
            throw sle;
        }
        catch (final Exception exception) {
            throw new SystemLoggedException(exception);
        }
    }

    /**
     * Sets the values from the {@link ResultSet} to the supplied data object.
     *
     * @param data          the instance to set the data to.
     * @param dataClz       the class representing the data.
     * @param resultSet     contains the data.
     * @param aliases       a {@link Map} containing the property name and an alias to use for the database column that
     *                      relates to the property.  If the property name represents a non-primitive Object, then every
     *                      property in that object will use the associated alias. Supply null to associate no aliases.
     * @param appliedAlias  the alias currently in use.  Set to null to not signal an alias in use.
     * @param dataPopulated contains an entry if at least one property on the supplied class was populated from the
     *                      {@link ResultSet}.
     * @throws Exception thrown for all exceptions.
     */
    @SuppressWarnings("PMD.ExcessiveParameterList")
    private void applyValues(final Object data, final Class<?> dataClz, final ResultSet resultSet,
            final Map<String, String> aliases, String appliedAlias, final Set<Short> dataPopulated)
            throws Exception {
        if (BOTTOM_TYPES.contains(dataClz)) {
            return;
        }

        // populate properties of all super classes first
        final Class<?> superDataClz = dataClz.getSuperclass();
        applyValues(data, superDataClz, resultSet, aliases, appliedAlias, dataPopulated);

        // populate properties of the supplied data instance
        final Field[] fields = dataClz.getDeclaredFields();
        for (final Field field : fields) {
            final int modifiers = field.getModifiers();
            if (Modifier.isStatic(modifiers)) {
                continue;
            }

            field.setAccessible(true);
            if (isNotModifiable(data, field)) {
                continue;
            }

            final Class<?> fieldType = field.getType();
            if (fieldType.isPrimitive() || fieldType.isEnum() || BASE_TYPES.contains(fieldType)) {
                applyValueFromColumnAnnotation(data, field, aliases, appliedAlias, dataPopulated);
            } else {
                applyValueFromNestedProperties(data, field, resultSet, aliases, appliedAlias, dataPopulated);
            }
        }

    }

    /**
     * Returns true if a value can NOT be applied to the supply field.
     *
     * @param data  the data to inspect.
     * @param field the field.
     * @return true if NOT modifiable.
     * @throws IllegalAccessException throw if the data cannot be retrieved.
     */
    private boolean isNotModifiable(final Object data, final Field field) throws IllegalAccessException {
        final boolean modifiable;
        final Object fieldValue = field.get(data);
        final int modifiers = field.getModifiers();
        if (Modifier.isFinal(modifiers) && fieldValue != null) {
            logger.warn(String.format(FINAL_FIELD_MSG, data.getClass().getName(), field.getName()));
            modifiable = false;
        } else {
            modifiable = true;
        }

        return !modifiable;
    }

    /**
     * Sets the value of the supplied data instance using the metadata from {@link Column} annotation.
     *
     * @param data          the instance to set the data to.
     * @param field         the field.
     * @param aliases       a {@link Map} containing the property name and an alias to use for the database column that
     *                      relates to the property.  If the property name represents a non-primitive Object, then every
     *                      property in that object will use the associated alias. Supply null to associate no aliases.
     * @param appliedAlias  the alias currently in use.  Set to null to not signal an alias in use.
     * @param dataPopulated contains an entry if at least one property on the supplied class was populated from the
     *                      {@link ResultSet}.
     * @throws Exception thrown for all exceptions.
     */
    @SuppressWarnings("PMD.ExcessiveParameterList")
    private void applyValueFromColumnAnnotation(final Object data, final Field field,
            final Map<String, String> aliases, final String appliedAlias, final Set<Short> dataPopulated)
            throws Exception {
        final Column colAnnotation = getColumnAnnotation(data, field);
        if (colAnnotation == null) {
            return;
        }

        final String propertyName = field.getName();
        final String databaseColumnName = colAnnotation.name();
        final String resultSetColumnName = getResultSetColumnName(propertyName, databaseColumnName, aliases,
                appliedAlias);
        if (resultSetManager.hasColumn(resultSetColumnName)) {
            final Object value = resultSetManager.getObject(resultSetColumnName);
            setFieldValue(data, field, value);

            if (value != null && dataPopulated.size() == 0) {
                short flag = 1;
                dataPopulated.add(flag);
            }
        }
    }

    /**
     * Returns the {@link Column} annotation if it exists on the supplied field or its accessor method.
     *
     * @param data  the object that contains the field.
     * @param field the field.
     * @return the annotation or null if it does not exist.
     */
    private Column getColumnAnnotation(final Object data, final Field field) {
        Column colAnnotation = field.getDeclaredAnnotation(Column.class);
        if (colAnnotation == null) {
            final String methodName = getAccessorMethodName(field);
            colAnnotation = getColumnAnnotationFromMethod(data, methodName);
        }
        return colAnnotation;
    }

    /**
     * Returns the java bean syntax representation of the accessor method name for the supplied field.
     *
     * @param field the field.
     * @return the accessor method name.
     */
    @SuppressWarnings("PMD.UseStringBufferForStringAppends")
    private String getAccessorMethodName(final Field field) {
        String methodName;
        final String propertyName = field.getName();
        final Class<?> clzType = field.getType();

        methodName = propertyName.substring(0, 1).toUpperCase(Locale.US) + propertyName.substring(1);
        if (clzType.isAssignableFrom(boolean.class)) {
            methodName = "is" + methodName;
        } else {
            methodName = "get" + methodName;
        }
        return methodName;
    }

    /**
     * Returns the {@link Column} annotation from the supplied method name on the supplied data object.
     *
     * @param data       the data object that contains the method.
     * @param methodName the method name.
     * @return the annotation or null if it does not exist.
     */
    private Column getColumnAnnotationFromMethod(final Object data, final String methodName) {
        Column colAnnotation;
        try {
            final Method method = data.getClass().getMethod(methodName);
            colAnnotation = method.getAnnotation(Column.class);
        }
        catch (final NoSuchMethodException nsme) {
            colAnnotation = null;
        }
        return colAnnotation;
    }

    /**
     * Assigns the supplied databaseValue to the field on the supplied data instance.
     *
     * @param data          the instance that contains the field to set the databaseValue to.
     * @param field         the field to set the databaseValue to.
     * @param databaseValue the databaseValue returned from the result set.
     * @throws Exception thrown for all exceptions.
     */
    private void setFieldValue(final Object data, final Field field, final Object databaseValue)
            throws Exception {
        final Class type = field.getType();
        if (databaseValue == null && type.isPrimitive()) {
            // can't assign a null databaseValue to a primitive type
            return;
        }

        final String fieldTypeName = type.getName().toUpperCase(Locale.US);
        if (databaseValue instanceof Long && isInteger(fieldTypeName)) {
            final int intValue = Math.toIntExact((Long) databaseValue);
            field.set(data, intValue);
        } else if (databaseValue instanceof BigDecimal && isInteger(fieldTypeName)) {
            final BigDecimal bdValue = (BigDecimal) databaseValue;
            field.set(data, bdValue.intValue());
        } else if (databaseValue instanceof BigDecimal && isByte(fieldTypeName)) {
            final BigDecimal bdValue = (BigDecimal) databaseValue;
            field.set(data, bdValue.byteValue());
        } else if (databaseValue instanceof BigDecimal && isBoolean(fieldTypeName)) {
            final BigDecimal bdValue = (BigDecimal) databaseValue;
            field.set(data, bdValue.byteValue() == 1);
        } else if (databaseValue instanceof Timestamp && fieldTypeName.equals("JAVA.SQL.TIME")) {
            final Timestamp ts = (Timestamp) databaseValue;
            field.set(data, new Time(ts.getTime()));
        } else if (databaseValue instanceof Timestamp && fieldTypeName.equals("JAVA.SQL.DATE")) {
            final Timestamp ts = (Timestamp) databaseValue;
            field.set(data, new java.sql.Date(ts.getTime()));
        } else if (databaseValue instanceof String && isBoolean(fieldTypeName)) {
            final boolean bool = ((String) databaseValue).equalsIgnoreCase("true");
            field.set(data, bool);
        } else if (type.isEnum()) {
            if (databaseValue instanceof String) {
                final String enumValue = (String) databaseValue;
                field.set(data, Enum.valueOf(type, enumValue));
            }
        } else {
            field.set(data, databaseValue);
        }
    }

    /**
     * Returns true if the supplied field type is an integer.
     *
     * @param fieldTypeName the field type.
     * @return true if integer.
     */
    private boolean isInteger(final String fieldTypeName) {
        return fieldTypeName.startsWith("INT") || fieldTypeName.contains("JAVA.LANG.INTEGER");
    }

    /**
     * Returns true if the supplied field type is a byte.
     *
     * @param fieldTypeName the field type.
     * @return true if byte.
     */
    private boolean isByte(final String fieldTypeName) {
        return fieldTypeName.startsWith("BYTE") || fieldTypeName.contains("JAVA.LANG.BYTE");
    }

    /**
     * Returns true if the supplied field type is a boolean.
     *
     * @param fieldTypeName the field type.
     * @return true if boolean.
     */
    private boolean isBoolean(final String fieldTypeName) {
        return fieldTypeName.startsWith("BOOLEAN") || fieldTypeName.contains("JAVA.LANG.BOOLEAN");
    }

    /**
     * Sets the value of the supplied data instance using the values embedded in a nested object.
     *
     * @param data          the instance to set the data to.
     * @param field         the field.
     * @param resultSet     contains the data.
     * @param aliases       a {@link Map} containing the property name and an alias to use for the database column that
     *                      relates to the property.  If the property name represents a non-primitive Object, then every
     *                      property in that object will use the associated alias. Supply null to associate no aliases.
     * @param appliedAlias  the alias currently in use.  Set to null to not signal an alias in use.
     * @param dataPopulated contains an entry if at least one property on the supplied class was populated from the
     *                      {@link ResultSet}.
     * @throws Exception thrown for all exceptions.
     */
    @SuppressWarnings("PMD.ExcessiveParameterList")
    private void applyValueFromNestedProperties(final Object data, final Field field,
            final ResultSet resultSet, final Map<String, String> aliases, String appliedAlias,
            final Set<Short> dataPopulated) throws Exception {
        final EmbeddedId embeddedId = field.getDeclaredAnnotation(EmbeddedId.class);
        final OneToOne oneAnnotation = field.getDeclaredAnnotation(OneToOne.class);
        if (embeddedId == null && oneAnnotation == null) {
            return;
        }

        final String propertyName = field.getName();
        if (aliases != null) {
            appliedAlias = appliedAlias == null ? aliases.get(propertyName) : appliedAlias;
        }

        final Class<?> fieldType = field.getType();
        final Object newDataObject = createInstance(fieldType, resultSet, aliases, appliedAlias);
        field.set(data, newDataObject);

        if (newDataObject != null && dataPopulated.size() == 0) {
            short flag = 1;
            dataPopulated.add(flag);
        }
    }

    /**
     * Returns the column name to be used to query the {@link ResultSet}.
     *
     * @param propertyName       the property name from the data instance.
     * @param databaseColumnName the database column name extracted from the annotation.
     * @param aliases            a {@link Map} containing the property name and an alias to use for the database column
     *                           that relates to the property.  If the property name represents a non-primitive Object,
     *                           then every property in that object will use the associated alias. Supply null to
     *                           associate no aliases.
     * @param appliedAlias       the alias currently in use.  Set to null to not signal an alias in use.
     * @return the column name to be used to query the {@link ResultSet}.
     */
    private String getResultSetColumnName(final String propertyName, final String databaseColumnName,
            final Map<String, String> aliases, final String appliedAlias) {
        String resultSetColumnName = databaseColumnName;
        if (appliedAlias != null) {
            resultSetColumnName = appliedAlias + "_" + databaseColumnName;
        } else if (aliases != null) {
            String aliasName = aliases.get(propertyName);
            if (aliasName != null) {
                resultSetColumnName = aliasName + "_" + databaseColumnName;
            }
        }
        return resultSetColumnName;
    }
}
