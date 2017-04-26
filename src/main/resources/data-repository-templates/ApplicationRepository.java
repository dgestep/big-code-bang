package ${topLevelDomain}.${companyName}.${productName}.model.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * Defines a repository that interacts with the database using Spring's JDBC template.
 *
 * @author ${codeAuthor}.
 */
public interface ApplicationRepository {
    /**
     * Returns Spring's {@link NamedParameterJdbcTemplate} instance.
     *
     * @return the instance.
     */
    NamedParameterJdbcTemplate getNamedParameterJdbcTemplate();

    /**
     * Returns Spring's {@link JdbcTemplate} instance.
     *
     * @return the instance.
     */
    JdbcTemplate getJdbcTemplate();
}
