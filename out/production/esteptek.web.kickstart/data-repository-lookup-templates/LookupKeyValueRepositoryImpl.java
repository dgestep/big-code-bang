package com.${companyName}.${productName}.model.repository.lookup;

import com.${companyName}.${productName}.model.data.LookupKeyValue;
import com.${companyName}.${productName}.model.repository.CrudRepositoryImpl;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Adds, updates, deletes, and retrieves data from the LOOKUP_KEY_VALUE table.
 *
 * @author ${codeAuthor}.
 */
@Repository("LookupKeyValueRepository")
public class LookupKeyValueRepositoryImpl extends CrudRepositoryImpl<LookupKeyValue>
        implements LookupKeyValueRepository {
    private static final int BUF_CAPACITY = 64;

    @Override
    @SuppressWarnings("PMD.ConsecutiveLiteralAppends")
    public int purgeExpiredEntries(final Date entryDate) {
        final StringBuilder sql = new StringBuilder(BUF_CAPACITY);
        sql.append("DELETE FROM LOOKUP_KEY_VALUE ");
        sql.append("WHERE CREATE_TS <= :CREATE_TS");

        final MapSqlParameterSource data = new MapSqlParameterSource();

        final LocalDateTime ldt = entryDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        final LocalDateTime createLocalDateTime = ldt.withHour(23).withMinute(59).withSecond(59); //NOCHECKSTYLE
        final ZonedDateTime zonedDateTime = createLocalDateTime.atZone(ZoneId.systemDefault());
        final Date createDate = Date.from(zonedDateTime.toInstant());

        data.addValue("CREATE_TS", createDate);
        return nativeSql().getNamedParameterJdbcTemplate().update(sql.toString(), data);
    }
}
