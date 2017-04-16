package com.${companyName}.${productName}.model.repository;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

/**
 * Extends Spring's {@link NamedParameterJdbcDaoSupport} to be used with native JDBC repositories.
 *
 * @author ${codeAuthor}.
 */
public class ApplicationRepositoryImpl extends NamedParameterJdbcDaoSupport implements ApplicationRepository {
}
