package com.${companyName}.${productName}.model.service.lookup;

import com.${companyName}.${productName}.model.data.LookupKeyValue;
import com.${companyName}.${productName}.model.data.LookupKeyValuePK;
import com.${companyName}.${productName}.model.data.MessageData;
import com.${companyName}.${productName}.model.exception.DataInputException;
import com.${companyName}.${productName}.model.repository.lookup.LookupKeyValueRepository;
import com.${companyName}.${productName}.model.service.user.UserServiceImpl;
import junit.framework.TestCase;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.Timestamp;
import java.util.Date;

import static org.junit.Assert.fail;

@RunWith(JMockit.class)
public class TestLookupKeyValueServiceImpl extends TestCase {
    @Injectable
    private LookupKeyValueRepository lookupKeyValueRepository;

    @Tested
    private LookupKeyValueServiceImpl sut;

    @Before
    @Override
    public void setUp() {
        sut = new LookupKeyValueServiceImpl();
    }

    private LookupKeyValue createData() {
        LookupKeyValuePK key = new LookupKeyValuePK();
        key.setGroupCode("group");
        key.setLookupName("name");

        LookupKeyValue data = new LookupKeyValue();
        data.setKey(key);
        data.setCreateTs(new Timestamp(System.currentTimeMillis()));
        data.setLookupValue("value");

        return data;
    }

    @Test
    public void testAddNoDataSupplied() {
        try {
            sut.add(null);

            fail("Should have failed");
        } catch (DataInputException die) {
            MessageData message = die.getFirstMessageData();
            Assert.assertEquals("G002", message.getCode());
            System.out.println(die.getMessagesAsString("\\n"));
        }
    }

    @Test
    public void testAddNoDefaultCreateDate() {
        LookupKeyValue data = createData();
        data.setCreateTs(null);

        sut.add(data);

        Assert.assertNotNull(data.getCreateTs());

        new Verifications() {
            {
                lookupKeyValueRepository.add(data);
                times = 1;
            }
        };
    }

    @Test
    public void testAddSuccess() {
        LookupKeyValue data = createData();

        sut.add(data);

        new Verifications() {
            {
                lookupKeyValueRepository.add(data);
                times = 1;
            }
        };
    }

    @Test
    public void testSaveSuccess() {
        LookupKeyValue data = createData();

        sut.save(data);

        new Verifications() {
            {
                lookupKeyValueRepository.save(data);
                times = 1;
            }
        };
    }

    @Test
    public void testDeleteSuccess() {
        LookupKeyValue data = createData();

        sut.delete(data);

        new Verifications() {
            {
                lookupKeyValueRepository.delete(LookupKeyValue.class, data.getKey());
                times = 1;
            }
        };
    }

    @Test
    public void testPurgeSuccess() {
        LookupKeyValue data = createData();


        sut.purgeExpiredEntries();

        new Verifications() {
            {
                lookupKeyValueRepository.purgeExpiredEntries((Date) any);
                times = 1;
            }
        };
    }

    @Test
    public void testRetrieve() {
        LookupKeyValue data = createData();

        sut.retrieve(data.getKey());

        new Verifications() {
            {
                lookupKeyValueRepository.retrieve(LookupKeyValue.class, data.getKey());
                times = 1;
            }
        };
    }
}
