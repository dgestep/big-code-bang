package ${topLevelDomain}.${companyName}.${productName}.model.repository.lookup;

import ${topLevelDomain}.${companyName}.${productName}.model.data.LookupKeyValue;
import ${topLevelDomain}.${companyName}.${productName}.model.data.LookupKeyValuePK;
import ${topLevelDomain}.${companyName}.${productName}.model.repository.ApplicationTestCase;
import ${topLevelDomain}.${companyName}.${productName}.model.repository.lookup.LookupKeyValueRepository;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class TestLookupKeyValueRepository extends ApplicationTestCase {

    @Resource(name = "LookupKeyValueRepository")
    private LookupKeyValueRepository lookupKeyValueRepository;

    @Test
    public void testPurge() {
        final LocalDateTime now = LocalDateTime.now().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime pastLocalDate = now.minusDays(5);
        final Date createDate = toDate(pastLocalDate);

        LookupKeyValue data = new LookupKeyValue("USER", "PurgeEntry", "DeleteMe");
        data.setCreateTs(new Timestamp(createDate.getTime()));
        lookupKeyValueRepository.add(data);

        LookupKeyValue retr = lookupKeyValueRepository.retrieve(LookupKeyValue.class, data.getKey());
        Assert.assertNotNull(retr);
        LookupKeyValuePK key = retr.getKey();
        Assert.assertEquals("USER", key.getGroupCode());
        Assert.assertEquals("PurgeEntry", key.getLookupName());
        Assert.assertEquals("DeleteMe", retr.getLookupValue());

        Date purgeDate = toDate(now.minusDays(5));

        int purgedCount = lookupKeyValueRepository.purgeExpiredEntries(purgeDate);
        Assert.assertTrue(purgedCount > 0);

        retr = lookupKeyValueRepository.retrieve(LookupKeyValue.class, key);
        Assert.assertNull("Didn't purge the entry", retr);
    }

    private Date toDate(final LocalDateTime dt) {
        ZonedDateTime zonedDateTime = dt.atZone(ZoneId.systemDefault());
        return Date.from(zonedDateTime.toInstant());
    }
}
