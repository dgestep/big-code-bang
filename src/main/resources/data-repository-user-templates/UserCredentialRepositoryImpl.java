package ${topLevelDomain}.${companyName}.${productName}.model.repository.user;

import ${topLevelDomain}.${companyName}.${productName}.model.data.UserCredential;
import ${topLevelDomain}.${companyName}.${productName}.model.repository.CrudRepositoryImpl;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

/**
 * Interacts with the user credential data repository.
 *
 * @author ${codeAuthor}.
 */
@Repository("UserCredentialRepository")
public class UserCredentialRepositoryImpl extends CrudRepositoryImpl<UserCredential>
        implements UserCredentialRepository {
    private static final int BUF_CAPACITY = 64;

    @Override
    @SuppressWarnings("PMD.ConsecutiveLiteralAppends")
    public void changePassword(UserCredential userCredential) {
        final StringBuilder sql = new StringBuilder(BUF_CAPACITY);
        sql.append("UPDATE USER_CREDENTIAL SET PASSWORD = :PASSWORD, ");
        sql.append("LAST_MODIFIED_TS = :LAST_MODIFIED_TS ");
        sql.append("WHERE UUID = :UUID ");

        final MapSqlParameterSource data = new MapSqlParameterSource();
        data.addValue("UUID", userCredential.getUuid());
        data.addValue("PASSWORD", userCredential.getPassword());
        data.addValue("LAST_MODIFIED_TS", userCredential.getLastModifiedTs());

        nativeSql().getNamedParameterJdbcTemplate().update(sql.toString(), data);
    }
}
