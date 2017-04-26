package ${topLevelDomain}.${companyName}.${productName}.model.repository;

import ${topLevelDomain}.${companyName}.${productName}.model.exception.SystemLoggedException;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.io.Reader;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * Manages the interaction with a {@link java.sql.ResultSet}.
 *
 * @author ${codeAuthor}.
 */
class ResultSetManager {
    private Set<String> columns;
    private ResultSet resultSet;
    private Set<String> clobColumns;
    private Set<String> blobColumns;

    /**
     * Creates an instance of this class and inspects and catalogs the supplied the metadata for the supplied {@link
     * ResultSet}.
     *
     * @param resultSet the {@link ResultSet} to manage.
     */
    ResultSetManager(final ResultSet resultSet) { // NOCHECKSTYLE
        this.resultSet = resultSet;

        catalogColumns();
    }

    /**
     * Keeps a set of all column names that exist in the {@link ResultSet}.
     */
    private void catalogColumns() {
        clobColumns = new HashSet<>();
        blobColumns = new HashSet<>();
        columns = new HashSet<>();
        try {
            final ResultSetMetaData rsmd = resultSet.getMetaData();
            final int count = rsmd.getColumnCount();
            for (int i = 1; i <= count; i++) {
                final String columnLabel = rsmd.getColumnLabel(i);
                if (columnLabel != null) {
                    // column label contains the either the column name or the alias if applicable.
                    columns.add(columnLabel);
                } else {
                    // Some JDBC driver implementations do not support the column label
                    columns.add(rsmd.getColumnName(i));
                }

                final String typeName = rsmd.getColumnTypeName(i);
                processClobColumn(rsmd, i, columnLabel, typeName);
                processBlobColumn(rsmd, i, columnLabel, typeName);
            }
        }
        catch (final SQLException sqle) {
            throw new SystemLoggedException(sqle);
        }
    }

    /**
     * Adds the supplied column label to the set of Clob column names if the column type is a CLOB.
     *
     * @param rsmd        contains the meta data about the database columns.
     * @param index       the index witin the column loop.
     * @param columnLabel the database column name.
     * @param typeName    the type of column.
     * @throws SQLException throw for all SQL exceptions.
     */
    private void processClobColumn(final ResultSetMetaData rsmd, final int index, final String columnLabel,
            final String typeName) throws SQLException {
        if ("CLOB".equalsIgnoreCase(typeName)) {
            if (columnLabel != null) {
                // column label contains the either the column name or the alias if applicable.
                clobColumns.add(columnLabel);
            } else {
                // Some JDBC driver implementations do not support the column label
                clobColumns.add(rsmd.getColumnName(index));
            }
        }
    }

    /**
     * Adds the supplied column label to the set of Blob column names if the column type is a BLOB.
     *
     * @param rsmd        contains the meta data about the database columns.
     * @param index       the index witin the column loop.
     * @param columnLabel the database column name.
     * @param typeName    the type of column.
     * @throws SQLException throw for all SQL exceptions.
     */
    private void processBlobColumn(final ResultSetMetaData rsmd, final int index, final String columnLabel,
            final String typeName) throws SQLException {
        if ("BLOB".equalsIgnoreCase(typeName)) {
            if (columnLabel != null) {
                // column label contains the either the column name or the alias if applicable.
                blobColumns.add(columnLabel);
            } else {
                // Some JDBC driver implementations do not support the column label
                blobColumns.add(rsmd.getColumnName(index));
            }
        }
    }

    /**
     * Returns true if the supplied column name exists in the {@link ResultSet}.
     *
     * @param columnName the column name.
     * @return true if the column name exists in the {@link ResultSet} associated with this instance.
     */
    public boolean hasColumn(final String columnName) {
        return columns.contains(columnName);
    }

    /**
     * Returns the value associated with the supplied column name from the {@link ResultSet} associated with this
     * instance.
     *
     * @param databaseColumnName the column name from the database table.
     * @return the value.
     */
    public Object getObject(final String databaseColumnName) {
        try {
            final Object value;
            if (clobColumns.contains(databaseColumnName)) {
                value = toString(databaseColumnName);
            } else if (blobColumns.contains(databaseColumnName)) {
                value = toBytes(databaseColumnName);
            } else {
                value = resultSet.getObject(databaseColumnName);
            }
            return value;
        }
        catch (final SQLException sqle) {
            throw new SystemLoggedException(sqle);
        }
    }

    /**
     * Converts the supplied Blob column to a byte array.
     *
     * @param databaseColumnName the Blob database column.
     * @return the byte array or null if the supplied column isn't a Blob or the stream is null from the database.
     */
    private byte[] toBytes(final String databaseColumnName) {
        final byte[] bytes;
        InputStream is = null;
        try {
            is = getBinaryStream(databaseColumnName);
            if (is == null) {
                bytes = null;
            } else {
                bytes = IOUtils.toByteArray(is);
            }
            return bytes;
        }
        catch (Exception exception) {
            throw new SystemLoggedException(exception);
        }
        finally {
            IOUtils.closeQuietly(is);
        }
    }

    /**
     * Returns an {@link InputStream} associated with a Blob column.  Clients are expected to close the stream after
     * use.
     *
     * @param databaseColumnName the Blob database column.
     * @return the {@link InputStream} or null if the supplied column isn't a Blob or the stream is null from the
     * database.
     */
    public InputStream getBinaryStream(final String databaseColumnName) {
        final InputStream is;
        try {
            if (blobColumns.contains(databaseColumnName)) {
                final Blob blob = resultSet.getBlob(databaseColumnName);
                is = blob.getBinaryStream();
            } else {
                is = null;
            }
        }
        catch (final SQLException sqle) {
            throw new SystemLoggedException(sqle);
        }
        return is;
    }

    /**
     * Converts the supplied Clob column to a String.
     *
     * @param databaseColumnName the Clob database column.
     * @return the string.
     */
    private String toString(final String databaseColumnName) {
        final String value;
        Reader is = null;
        try {
            is = getCharacterStream(databaseColumnName);
            if (is == null) {
                value = null;
            } else {
                value = IOUtils.toString(is);
            }
            return value;
        }
        catch (Exception exception) {
            throw new SystemLoggedException(exception);
        }
        finally {
            IOUtils.closeQuietly(is);
        }
    }

    /**
     * Returns an {@link Reader} associated with a Clob column.  Clients are expected to close the reader after use.
     *
     * @param databaseColumnName the Clob database column.
     * @return the {@link Reader} or null if the supplied column isn't a Clob or the reader is null from the database.
     */
    public Reader getCharacterStream(final String databaseColumnName) {
        final Reader is;
        try {
            if (clobColumns.contains(databaseColumnName)) {
                final Clob clob = resultSet.getClob(databaseColumnName);
                if (clob == null) {
                    is = null;
                } else {
                    is = clob.getCharacterStream();
                }
            } else {
                is = null;
            }
        }
        catch (final SQLException sqle) {
            throw new SystemLoggedException(sqle);
        }
        return is;
    }
}
