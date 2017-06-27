package ${topLevelDomain}.${companyName}.${productName}.model.repository.user;

import ${topLevelDomain}.${companyName}.${productName}.model.data.UserToken;
import ${topLevelDomain}.${companyName}.${productName}.model.repository.ApplicationTestCase;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.UUID;

public class TestUserToken extends ApplicationTestCase {

    @Resource(name = "UserTokenRepository")
    private UserTokenRepository userTokenRepository;

    @Test
    public void testUpdateByEmail() {
        UserToken data = new UserToken();
        data.setCreateTs(new Timestamp(System.currentTimeMillis()));
        data.setEmailAddress("from@gmail.com");
        data.setTokenUuid(UUID.randomUUID().toString());
        data.setUserUuid("user1");
        userTokenRepository.add(data);

        UserToken retr = userTokenRepository.retrieve(UserToken.class, data.getTokenUuid());
        Assert.assertNotNull(retr);

        int rows = userTokenRepository.updateByEmail("from@gmail.com", "to@gmail.com");
        Assert.assertEquals(1, rows);
        retr = userTokenRepository.retrieve(UserToken.class, data.getTokenUuid());
        Assert.assertNotNull(retr);
        Assert.assertEquals("to@gmail.com", retr.getEmailAddress());
    }
}
