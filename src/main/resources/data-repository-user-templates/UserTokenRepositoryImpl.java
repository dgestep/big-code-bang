package ${topLevelDomain}.${companyName}.${productName}.model.repository.user;

import ${topLevelDomain}.${companyName}.${productName}.model.data.UserToken;
import ${topLevelDomain}.${companyName}.${productName}.model.repository.CrudRepositoryImpl;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

/**
 * Adds, updates, deletes, and queries source information from the database.
 *
 * @author dougestep
 */
@Repository("UserTokenRepository")
public class UserTokenRepositoryImpl extends CrudRepositoryImpl<UserToken> implements UserTokenRepository {
    private static final int BUF_CAPACITY = 64;

    @Override
    @SuppressWarnings("PMD.ConsecutiveLiteralAppends") // NOCHECKSTYLE
    public int updateByEmail(String fromEmailAddress, String toEmailAddress) {
        final StringBuilder buf = new StringBuilder(BUF_CAPACITY);
        buf.append("UPDATE USER_TOKEN SET EMAIL_ADDRESS = :TO_EMAIL_ADDRESS ");
        buf.append("WHERE EMAIL_ADDRESS = :FROM_EMAIL_ADDRESS");

        final MapSqlParameterSource data = new MapSqlParameterSource();
        data.addValue("FROM_EMAIL_ADDRESS", fromEmailAddress);
        data.addValue("TO_EMAIL_ADDRESS", toEmailAddress);
        return nativeSql().getNamedParameterJdbcTemplate().update(buf.toString(), data);
    }
}
