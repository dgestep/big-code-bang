package ${topLevelDomain}.${companyName}.${productName}.model.repository;

import java.util.List;

/**
 * Helper class for repositories.
 *
 * @author ${codeAuthor}.
 */
public final class RepositoryHelper {
    private static final char COMMA = ',';

    /**
     * Creates an instance of this class.
     */
    private RepositoryHelper() {
    }

    /**
     * Returns the supplied list of ID's in the form of an SQL IN clause.
     *
     * @param typeIds the type ID's.
     * @return the IN clause.
     */
    public static String createInClause(final List<Integer> typeIds) {
        final StringBuilder buf = new StringBuilder();
        for (final int typeId : typeIds) {
            buf.append(typeId).append(COMMA);
        }

        final String str = buf.toString();
        final int idx = str.lastIndexOf(COMMA);
        return str.substring(0, idx);
    }

}
