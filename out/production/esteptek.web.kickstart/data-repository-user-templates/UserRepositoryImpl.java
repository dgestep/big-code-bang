package com.${companyName}.${productName}.model.repository.user;

import com.${companyName}.${productName}.model.repository.DataSet;
import com.${companyName}.${productName}.model.criteria.UserSearchCriteriaData;
import com.${companyName}.${productName}.model.data.RoleConverter;
import com.${companyName}.${productName}.model.data.UserCredential;
import com.${companyName}.${productName}.model.data.UserProfile;
import com.${companyName}.${productName}.model.repository.CrudRepositoryImpl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Adds, updates, deletes, and queries source information from the database.
 *
 * @author ${codeAuthor}.
 */
@Repository("UserRepository")
public class UserRepositoryImpl extends CrudRepositoryImpl<UserProfile> implements UserRepository {
    private static final int BUF_CAPACITY = 64;
    private static final String SINGLE = "'";
    private static final String COMMA = ",";

    @Override
    public List<UserProfile> search(final UserSearchCriteriaData criteria) {
        final StringBuilder sql = new StringBuilder(BUF_CAPACITY);
        sql.append(createMultiUserSql(criteria));
        sql.append("ORDER BY UP.LAST_NAME ASC, UP.FIRST_NAME ASC, UP.EMAIL_ADDRESS ASC");

        final MapSqlParameterSource data = createMultiUserData(criteria);
        return nativeSql().getNamedParameterJdbcTemplate().query(sql.toString(), data, (rs, rowNum) -> {
            final DataSet dataSet = new DataSet();
            final UserProfile user = dataSet.instanceOf(UserProfile.class, rs);

            final RoleConverter converter = new RoleConverter();
            user.setRole(converter.convertToEntityAttribute(rs.getInt("ROLE_ID")));

            final UserCredential userCredential = dataSet.instanceOf(UserCredential.class, rs);
            user.setUserCredential(userCredential);

            return user;
        });
    }

    /**
     * Returns the SQL for a multi-user search.
     *
     * @param criteria the search criteria to apply.
     * @return the SQL.
     */
    @SuppressWarnings("PMD.ConsecutiveLiteralAppends")
    private String createMultiUserSql(final UserSearchCriteriaData criteria) {
        final boolean uniqueRow = criteria != null && (StringUtils.isNotEmpty(criteria.getUuid()) || StringUtils
                .isNotEmpty(criteria.getEmailAddress()));
        final StringBuilder sql = new StringBuilder(BUF_CAPACITY);

        sql.append("SELECT * ");
        sql.append("FROM USER_PROFILE UP ");
        if (uniqueRow) {
            sql.append(" JOIN USER_CREDENTIAL UC ON UP.UUID = UC.UUID ");
        }
        sql.append("WHERE 1 = 1 ");

        if (criteria == null) {
            return sql.toString();
        }

        if (StringUtils.isNotEmpty(criteria.getUuid())) {
            sql.append("AND UP.UUID = :UUID ");
        }

        final List<String> uuids = criteria.getUuids();
        if (CollectionUtils.isNotEmpty(uuids)) {
            sql.append("AND UP.UUID IN (");
            sql.append(getSqlForUserList(uuids));
            sql.append(") ");
        }

        if (StringUtils.isNotEmpty(criteria.getEmailAddress())) {
            sql.append("AND UP.EMAIL_ADDRESS LIKE :EMAIL_ADDRESS ");
        }

        if (StringUtils.isNotEmpty(criteria.getActiveFlag())) {
            sql.append("AND UP.ACTIVE_FLAG = :ACTIVE_FLAG ");
        }

        if (criteria.getRole() != null) {
            sql.append("AND UP.ROLE_ID = :ROLE_ID ");
        }

        if (StringUtils.isNotEmpty(criteria.getFirstName())) {
            sql.append("AND UP.FIRST_NAME LIKE :FIRST_NAME ");
        }

        if (StringUtils.isNotEmpty(criteria.getLastName())) {
            sql.append("AND UP.LAST_NAME LIKE :LAST_NAME ");
        }

        return sql.toString();
    }

    /**
     * Returns the SQL necessary for a multi-user UUID query.
     *
     * @param uuids the UUID's.
     * @return the SQL.
     */
    private String getSqlForUserList(final List<String> uuids) {
        final StringBuilder buf = new StringBuilder();
        for (final String uuid : uuids) {
            buf.append(SINGLE);
            buf.append(StringUtils.replace(uuid, SINGLE, "''"));
            buf.append(SINGLE).append(COMMA);
        }
        final String s = buf.toString();
        final int idx = s.lastIndexOf(COMMA);
        return s.substring(0, idx);
    }

    /**
     * Returns the data map containing the provided criteria.
     *
     * @param criteria the criteria.
     * @return the map.
     */
    private MapSqlParameterSource createMultiUserData(final UserSearchCriteriaData criteria) {
        final MapSqlParameterSource data = new MapSqlParameterSource();
        if (criteria == null) {
            return data;
        }

        if (StringUtils.isNotEmpty(criteria.getUuid())) {
            data.addValue("UUID", criteria.getUuid());
        }
        if (StringUtils.isNotEmpty(criteria.getEmailAddress())) {
            data.addValue("EMAIL_ADDRESS", criteria.getEmailAddress() + "%");
        }
        if (StringUtils.isNotEmpty(criteria.getActiveFlag())) {
            data.addValue("ACTIVE_FLAG", criteria.getActiveFlag());
        }
        if (criteria.getRole() != null) {
            data.addValue("ROLE_ID", criteria.getRole().getId());
        }
        if (StringUtils.isNotEmpty(criteria.getFirstName())) {
            data.addValue("FIRST_NAME", criteria.getFirstName() + "%");
        }
        if (StringUtils.isNotEmpty(criteria.getLastName())) {
            data.addValue("LAST_NAME", criteria.getLastName() + "%");
        }
        return data;
    }
}
