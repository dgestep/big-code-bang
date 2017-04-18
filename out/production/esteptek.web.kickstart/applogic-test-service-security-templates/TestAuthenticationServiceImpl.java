package com.${companyName}.${productName}.model.service.security;

import com.${companyName}.${productName}.model.criteria.UserSearchCriteriaData;
import com.${companyName}.${productName}.model.data.MessageData;
import com.${companyName}.${productName}.model.data.UserCredential;
import com.${companyName}.${productName}.model.data.UserData;
import com.${companyName}.${productName}.model.data.UserProfile;
import com.${companyName}.${productName}.model.data.UserToken;
import com.${companyName}.${productName}.model.enumeration.Role;
import com.${companyName}.${productName}.model.enumeration.message.SecurityMessage;
import com.${companyName}.${productName}.model.exception.DataInputException;
import com.${companyName}.${productName}.model.repository.CrudRepository;
import com.${companyName}.${productName}.model.repository.user.PasswordValidator;
import com.${companyName}.${productName}.model.repository.user.UserRepository;
import junit.framework.TestCase;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RunWith(JMockit.class)
public class TestAuthenticationServiceImpl extends TestCase {
    private static final String DELIM = "|";

    @Injectable
    private UserRepository userRepository;

    @Injectable
    private CrudRepository<UserToken> userTokenRepository;

    @Injectable
    private PasswordValidator passwordValidator;

    @Mocked
    private StandardPBEStringEncryptor encryptor;

    @Tested
    private AuthenticationServiceImpl sut;

    @Before
    @Override
    public void setUp() {
        sut = new AuthenticationServiceImpl();
    }

    @Test
    public void testAuthenticateUserPassNotSupplied() {
        try {
            sut.authenticate(null, null, false);
            fail("Should have failed");
        }
        catch (DataInputException die) {
            List<MessageData> messages = die.getMessageData();
            Assert.assertEquals(2, messages.size());
            Assert.assertEquals("G001", messages.get(0).getCode());
            Assert.assertEquals("G001", messages.get(1).getCode());
            System.out.println(die.getMessagesAsString("\\n"));
        }
    }

    @Test
    public void testAuthenticateEmailNotFound() {
        new Expectations() {
            {
                userRepository.search((UserSearchCriteriaData) any);
                result = new ArrayList<>();
            }
        };

        try {
            sut.authenticate("test@gmail.com", "bogus", false);
            fail("Should have failed");
        }
        catch (DataInputException die) {
            MessageData message = die.getFirstMessageData();
            Assert.assertEquals("G003", message.getCode());
            System.out.println(die.getMessagesAsString("\\n"));
        }
    }

    @Test
    public void testAuthenticateUserNotActive() {
        new Expectations() {
            {
                userRepository.search((UserSearchCriteriaData) any);
                UserProfile userProfile = new UserProfile();
                userProfile.setUuid(UUID.randomUUID().toString());
                userProfile.setActiveFlag("N");

                result = Arrays.asList(new UserProfile[] { userProfile });
            }
        };

        try {
            sut.authenticate("test@gmail.com", "bogus", false);
            fail("Should have failed");
        }
        catch (DataInputException die) {
            MessageData message = die.getFirstMessageData();
            Assert.assertEquals("U003", message.getCode());
            System.out.println(die.getMessagesAsString("\\n"));
        }
    }

    @Test
    public void testAuthenticateIncorrectPassword() {
        new Expectations() {
            {
                userRepository.search((UserSearchCriteriaData) any);

                String uuid = UUID.randomUUID().toString();
                UserProfile userProfile = new UserProfile();
                userProfile.setUuid(uuid);
                userProfile.setActiveFlag("Y");

                UserCredential credential = new UserCredential(uuid);
                userProfile.setUserCredential(credential);

                result = Arrays.asList(new UserProfile[] { userProfile });

                passwordValidator.isNotValidPassword(anyString, anyString);
                result = true;
            }
        };

        try {
            sut.authenticate("test@gmail.com", "bogus", false);
            fail("Should have failed");
        }
        catch (DataInputException die) {
            MessageData message = die.getFirstMessageData();
            Assert.assertEquals("U001", message.getCode());
            System.out.println(die.getMessagesAsString("\\n"));
        }
    }

    private String createTestTokenString(final String emailAddress) {
        final Date createTs = new Date();
        final long timestamp = createTs.getTime();
        final String tokenUuid = UUID.randomUUID().toString();
        return tokenUuid + DELIM + emailAddress + DELIM + timestamp;
    }

    private UserProfile createUserProfile(final String userUuid, final String emailAddress) {
        UserProfile userProfile = new UserProfile();
        userProfile.setUuid(userUuid);
        userProfile.setActiveFlag("Y");
        userProfile.setRole(Role.ADMIN);
        userProfile.setEmailAddress(emailAddress);

        UserCredential credential = new UserCredential(userUuid);
        userProfile.setUserCredential(credential);

        return userProfile;
    }

    private void verifySuccessfulAuthentication() {
        new Verifications() {
            {
                userRepository.save((UserProfile) any);
                times = 1;

                userTokenRepository.save((UserToken) any);
                times = 1;
            }
        };
    }

    private void assertAuthUserData(String emailAddress, String encryptedText, String userUuid, UserData authUserData) {
        Assert.assertNotNull(authUserData);
        Assert.assertEquals(emailAddress, authUserData.getEmailAddress());
        Assert.assertEquals(authUserData.getToken(), encryptedText);
        Assert.assertEquals(Role.ADMIN.getValue(), authUserData.getRole());
        Assert.assertEquals(userUuid, authUserData.getUserUuid());
    }

    @Test
    public void testAuthenticateNoAutoLogin() {
        final String emailAddress = "test@gmail.com";
        final String encryptedText = createTestTokenString(emailAddress);
        final String userUuid = UUID.randomUUID().toString();

        new Expectations() {
            {
                userRepository.search((UserSearchCriteriaData) any);
                UserProfile userProfile = createUserProfile(userUuid, emailAddress);
                result = Arrays.asList(new UserProfile[] { userProfile });

                passwordValidator.isNotValidPassword(anyString, anyString);
                result = false;

                encryptor.encrypt(anyString);
                result = encryptedText;
            }
        };

        UserData authUserData = sut.authenticate(emailAddress, "bogus", false);
        verifySuccessfulAuthentication();
        assertAuthUserData(emailAddress, encryptedText, userUuid, authUserData);
    }

    @Test
    public void testAuthenticateAutoLogin() {
        final String emailAddress = "test@gmail.com";
        final String encryptedText = createTestTokenString(emailAddress);
        final String userUuid = UUID.randomUUID().toString();

        new Expectations() {
            {
                userRepository.search((UserSearchCriteriaData) any);

                UserProfile userProfile = createUserProfile(userUuid, emailAddress);
                result = Arrays.asList(new UserProfile[] { userProfile });

                encryptor.encrypt(anyString);
                result = encryptedText;
            }
        };

        UserData authUserData = sut.authenticate(emailAddress, "bogus", true);
        verifySuccessfulAuthentication();
        assertAuthUserData(emailAddress, encryptedText, userUuid, authUserData);
    }

    @Test
    public void testApplyToken() {
        final Date createTs = new Date();
        final long timestamp = createTs.getTime();
        final String username = "TESTUSER";
        final String uuid = UUID.randomUUID().toString();
        final String token = uuid + DELIM + username + DELIM + timestamp;

        UserToken retr = new UserToken();
        retr.setTokenUuid(uuid);
        retr.setCreateTs(new Timestamp(timestamp));
        retr.setLastModifiedTs(new Timestamp(timestamp));

        new Expectations() {
            {
                encryptor.decrypt(token);
                result = token;

                userTokenRepository.retrieve(UserToken.class, any);
                result = retr;
            }
        };
        sut.applyToken(token);

        new Verifications() {
            {
                userTokenRepository.save((UserToken) any);
                times = 1;
            }
        };
    }

    @Test
    public void testApplyBadToken() {
        final String token = "bad token";

        new Expectations() {
            {
                encryptor.decrypt(token);
                result = new EncryptionOperationNotPossibleException();
            }
        };
        UserData userData = sut.applyToken(token);

        new Verifications() {
            {
                userTokenRepository.save((UserToken) any);
                times = 0;
            }
        };

        Assert.assertNull(userData);
    }

    @Test
    public void testApplyIncompleteToken() {
        final String uuid = UUID.randomUUID().toString();
        final String token = uuid + DELIM + "email";

        new Expectations() {
            {
                encryptor.decrypt(token);
                result = token;
            }
        };
        UserData userData = sut.applyToken(token);

        new Verifications() {
            {
                userTokenRepository.save((UserToken) any);
                times = 0;
            }
        };

        Assert.assertNull(userData);
    }

    @Test
    public void testInvalidateMissingToken() {
        try {
            sut.invalidate(null);

            fail("Should have failed");
        }
        catch (DataInputException die) {
            List<MessageData> messages = die.getMessageData();
            Assert.assertEquals(1, messages.size());
            String code = messages.get(0).getCode();
            Assert.assertEquals(SecurityMessage.SC003.toString(), code);
        }

        new Verifications() {
            {
                userTokenRepository.delete(UserToken.class, anyString);
                times = 0;
            }
        };
    }

    @Test
    public void testInvalidBadToken() {
        final String token = "bad token";

        new Expectations() {
            {
                encryptor.decrypt(token);
                result = new EncryptionOperationNotPossibleException();
            }
        };

        sut.invalidate(token);

        new Verifications() {
            {
                userTokenRepository.delete(UserToken.class, anyString);
                times = 0;
            }
        };
    }

    @Test
    public void testInvalidateIncompleteToken() {
        final String username = "TESTUSER";
        final String uuid = UUID.randomUUID().toString();
        final String token = uuid + DELIM + username;

        new Expectations() {
            {
                encryptor.decrypt(token);
                result = token;
            }
        };
        sut.invalidate(token);

        new Verifications() {
            {
                userTokenRepository.delete(UserToken.class, anyString);
                times = 0;
            }
        };
    }

    @Test
    public void testInvalidateToken() {
        final Date createTs = new Date();
        final long timestamp = createTs.getTime();
        final String username = "TESTUSER";
        final String uuid = UUID.randomUUID().toString();
        final String token = uuid + DELIM + username + DELIM + timestamp;

        new Expectations() {
            {
                encryptor.decrypt(token);
                result = token;

                userTokenRepository.retrieve(UserToken.class, any);
                result = new UserToken();
            }
        };
        sut.invalidate(token);

        new Verifications() {
            {
                userTokenRepository.delete(UserToken.class, anyString);
                times = 1;
            }
        };
    }

    @Test
    public void testInvalidateTokenMissingFromDb() {
        new Expectations() {
            {
                encryptor.decrypt("1|2|3");
                result = "1|2|3";

                userTokenRepository.retrieve(UserToken.class, any);
                result = null;
            }
        };
        sut.invalidate("1|2|3");

        new Verifications() {
            {
                userTokenRepository.delete(UserToken.class, anyString);
                times = 0;
            }
        };
    }
}
