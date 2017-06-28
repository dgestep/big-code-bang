package ${topLevelDomain}.${companyName}.${productName}.model.service.user;

import ${topLevelDomain}.${companyName}.${productName}.model.criteria.UserSearchCriteriaData;
import ${topLevelDomain}.${companyName}.${productName}.model.data.LookupKeyValue;
import ${topLevelDomain}.${companyName}.${productName}.model.data.MessageData;
import ${topLevelDomain}.${companyName}.${productName}.model.data.UserCredential;
import ${topLevelDomain}.${companyName}.${productName}.model.data.UserProfile;
import ${topLevelDomain}.${companyName}.${productName}.model.enumeration.Role;
import ${topLevelDomain}.${companyName}.${productName}.model.exception.DataInputException;
import ${topLevelDomain}.${companyName}.${productName}.model.repository.CrudRepository;
import ${topLevelDomain}.${companyName}.${productName}.model.repository.mail.MailRepository;
import ${topLevelDomain}.${companyName}.${productName}.model.repository.user.PasswordGeneratorRepository;
import ${topLevelDomain}.${companyName}.${productName}.model.repository.user.PasswordValidator;
import ${topLevelDomain}.${companyName}.${productName}.model.repository.user.UserCredentialValidator;
import ${topLevelDomain}.${companyName}.${productName}.model.repository.user.UserRepository;
import ${topLevelDomain}.${companyName}.${productName}.model.service.lookup.LookupKeyValueService;
import junit.framework.TestCase;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

@RunWith(JMockit.class)
public class TestUserServiceImpl extends TestCase {
    @Injectable
    private UserRepository userRepository;

    @Injectable
    private CrudRepository<UserCredential> userCredentialRepository;

    @Injectable
    private UserCredentialValidator userCredentialValidator;

    @Injectable
    private LookupKeyValueService lookupKeyValueService;

    @Injectable
    private PasswordGeneratorRepository passwordGeneratorRepository;

    @Injectable
    private UserTokenRepository userTokenRepository;

    @Injectable
    private MailRepository emailRepository;

    @Injectable
    private PasswordValidator passwordValidator;

    @Mocked
    private StandardPBEStringEncryptor encryptor;

    @Tested
    private UserServiceImpl sut;

    @Before
    @Override
    public void setUp() {
        sut = new UserServiceImpl();
    }

    @Test
    public void testAddUserAlreadyExists() {
        new Expectations() {
            {
                userRepository.search((UserSearchCriteriaData) any);
                UserProfile userProfile = new UserProfile();
                userProfile.setUuid(UUID.randomUUID().toString());

                result = Arrays.asList(new UserProfile[] { userProfile });
            }
        };

        UserProfile data = new UserProfile();
        data.setEmailAddress("test@gmail.com");
        data.setFirstName("Test");
        data.setLastName("User");
        data.setRole(Role.ADMIN);

        try {
            sut.add(data);

            fail("Should have failed");
        }
        catch (DataInputException die) {
            MessageData message = die.getFirstMessageData();
            Assert.assertEquals("U007", message.getCode());
            System.out.println(die.getMessagesAsString("\\n"));
        }
    }

    @Test
    public void testAddUuidAndCredentialNull() {
        new Expectations() {
            {
                userRepository.search((UserSearchCriteriaData) any);
                result = new ArrayList<>();
            }
        };

        UserProfile data = new UserProfile();
        data.setEmailAddress("test@gmail.com");
        data.setFirstName("Test");
        data.setLastName("User");

        sut.add(data);

        Assert.assertNotNull(data.getUuid());
        Assert.assertNotNull(data.getCreateTs());
        Assert.assertNotNull(data.getLastModifiedTs());
        Assert.assertEquals("Y", data.getActiveFlag());
        Assert.assertEquals("USER", data.getRole().getValue());

        new Verifications() {
            {
                userRepository.add((UserProfile) any);
                times = 1;

                userCredentialRepository.add((UserCredential) any);
                times = 1;
            }
        };
    }

    @Test
    public void testAddUuidAndCredentialCredentialsEmptyPassword() {
        new Expectations() {
            {
                userRepository.search((UserSearchCriteriaData) any);
                result = new ArrayList<>();
            }
        };

        UserProfile data = new UserProfile();
        data.setEmailAddress("test@gmail.com");
        data.setFirstName("Test");
        data.setLastName("User");

        UserCredential credential = new UserCredential();
        credential.setPassword(null);
        data.setUserCredential(credential);

        sut.add(data);

        Assert.assertNotNull(data.getUuid());
        Assert.assertNotNull(data.getCreateTs());
        Assert.assertNotNull(data.getLastModifiedTs());
        Assert.assertEquals("Y", data.getActiveFlag());
        Assert.assertEquals("USER", data.getRole().getValue());

        new Verifications() {
            {
                userRepository.add((UserProfile) any);
                times = 1;

                userCredentialRepository.add((UserCredential) any);
                times = 1;
            }
        };
    }

    @Test
    public void testAddUuidAndCredentialSet() {
        new Expectations() {
            {
                userRepository.search((UserSearchCriteriaData) any);
                result = new ArrayList<>();
            }
        };

        String uuid = UUID.randomUUID().toString();
        UserProfile data = new UserProfile();
        data.setUuid(uuid);
        data.setEmailAddress("test@gmail.com");
        data.setFirstName("Test");
        data.setLastName("User");

        UserCredential credential = new UserCredential(uuid);
        credential.setPassword("Testing12345");
        data.setUserCredential(credential);

        sut.add(data);

        Assert.assertEquals(uuid, data.getUuid());
        Assert.assertNotNull(data.getCreateTs());
        Assert.assertNotNull(data.getLastModifiedTs());
        Assert.assertEquals("Y", data.getActiveFlag());
        Assert.assertEquals("USER", data.getRole().getValue());

        new Verifications() {
            {
                userRepository.add((UserProfile) any);
                times = 1;

                userCredentialRepository.add((UserCredential) any);
                times = 1;
            }
        };
    }

    @Test
    public void testAddNoProfileSupplied() {
        try {
            sut.add(null);

            fail("Should have failed");
        }
        catch (DataInputException die) {
            MessageData message = die.getFirstMessageData();
            Assert.assertEquals("G002", message.getCode());
            System.out.println(die.getMessagesAsString("\\n"));
        }
    }

    @Test
    public void testSaveUserAlreadyExistsByEmail() {
        new Expectations() {
            {
                UserProfile originalProfile = new UserProfile();
                originalProfile.setEmailAddress("original@gmail.com");
                originalProfile.setUuid(UUID.randomUUID().toString());

                userRepository.retrieve(UserProfile.class, anyString);
                result = originalProfile;

                userRepository.search((UserSearchCriteriaData) any);
                UserProfile userProfile = new UserProfile();
                userProfile.setUuid(UUID.randomUUID().toString());
                userProfile.setEmailAddress("existing@gmail.com");

                result = Arrays.asList(new UserProfile[] { userProfile });
            }
        };

        UserProfile data = new UserProfile();
        data.setUuid(UUID.randomUUID().toString());
        data.setEmailAddress("existing@gmail.com");

        try {
            sut.save(data);

            fail("Should have failed");
        }
        catch (DataInputException die) {
            MessageData message = die.getFirstMessageData();
            Assert.assertEquals("U007", message.getCode());
            System.out.println(die.getMessagesAsString("\\n"));
        }
    }

    @Test
    public void testSaveUserSuccessChangingNothing() {
        new Expectations() {
            {
                UserProfile originalProfile = new UserProfile();
                originalProfile.setEmailAddress("original@gmail.com");
                originalProfile.setUuid(UUID.randomUUID().toString());
                originalProfile.setFirstName("Test");
                originalProfile.setLastName("User");
                originalProfile.setCreateTs(new Timestamp(System.currentTimeMillis()));
                originalProfile.setActiveFlag("Y");
                originalProfile.setLastLoggedTs(new Timestamp(System.currentTimeMillis()));
                originalProfile.setRole(Role.ADMIN);

                userRepository.retrieve(UserProfile.class, anyString);
                result = originalProfile;
            }
        };

        UserProfile data = new UserProfile();
        data.setUuid(UUID.randomUUID().toString());
        sut.save(data);

        new Verifications() {
            {
                userRepository.save((UserProfile) any);
                times = 1;
            }
        };
    }

    @Test
    public void testSaveUserSuccessChangingEverything() {
        new Expectations() {
            {
                UserProfile originalProfile = new UserProfile();
                originalProfile.setEmailAddress("original@gmail.com");
                originalProfile.setUuid(UUID.randomUUID().toString());
                originalProfile.setFirstName("Test");
                originalProfile.setLastName("User");
                originalProfile.setCreateTs(new Timestamp(System.currentTimeMillis()));

                userRepository.retrieve(UserProfile.class, anyString);
                result = originalProfile;
            }
        };

        UserProfile data = new UserProfile();
        data.setUuid(UUID.randomUUID().toString());
        data.setEmailAddress("original2@gmail.com");
        data.setFirstName("Test2");
        data.setLastName("User2");
        data.setCreateTs(new Timestamp(System.currentTimeMillis()));
        data.setActiveFlag("N");
        data.setLastLoggedTs(new Timestamp(System.currentTimeMillis()));
        data.setRole(Role.USER);

        sut.save(data);

        new Verifications() {
            {
                userRepository.save((UserProfile) any);
                times = 1;
            }
        };
    }

    @Test
    public void testSaveUserSuccessChangingEverythingInvalidActiveFlag() {
        new Expectations() {
            {
                UserProfile originalProfile = new UserProfile();
                originalProfile.setEmailAddress("original@gmail.com");
                originalProfile.setUuid(UUID.randomUUID().toString());
                originalProfile.setFirstName("Test");
                originalProfile.setLastName("User");
                originalProfile.setCreateTs(new Timestamp(System.currentTimeMillis()));

                userRepository.retrieve(UserProfile.class, anyString);
                result = originalProfile;
            }
        };

        UserProfile data = new UserProfile();
        data.setUuid(UUID.randomUUID().toString());
        data.setEmailAddress("original2@gmail.com");
        data.setFirstName("Test2");
        data.setLastName("User2");
        data.setCreateTs(new Timestamp(System.currentTimeMillis()));
        data.setActiveFlag("X");
        data.setLastLoggedTs(new Timestamp(System.currentTimeMillis()));
        data.setRole(Role.USER);

        sut.save(data);

        new Verifications() {
            {
                userRepository.save((UserProfile) any);
                times = 1;
            }
        };
    }

    @Test
    public void testSaveUserProfileNotSupplied() {
        sut.save(null);

        new Verifications() {
            {
                userRepository.save((UserProfile) any);
                times = 0;
            }
        };
    }

    @Test
    public void testSaveUserProfileUuidNotSupplied() {
        UserProfile data = new UserProfile();
        sut.save(data);

        new Verifications() {
            {
                userRepository.save((UserProfile) any);
                times = 0;
            }
        };
    }

    @Test
    public void testSaveUserProfileUserNotFound() {
        new Expectations() {
            {
                userRepository.retrieve(UserProfile.class, anyString);
                result = null;
            }
        };

        UserProfile data = new UserProfile();
        data.setUuid(UUID.randomUUID().toString());

        sut.save(data);

        new Verifications() {
            {
                userRepository.save((UserProfile) any);
                times = 0;
            }
        };
    }

    @Test
    public void testDeleteUserNullProfile() {
        sut.delete(null);

        new Verifications() {
            {
                userCredentialRepository.delete(UserCredential.class, anyString);
                times = 0;

                userRepository.delete(UserProfile.class, anyString);
                times = 0;
            }
        };
    }

    @Test
    public void testDeleteUserNoUUID() {
        UserProfile data = new UserProfile();
        sut.delete(data);

        new Verifications() {
            {
                userCredentialRepository.delete(UserCredential.class, anyString);
                times = 0;

                userRepository.delete(UserProfile.class, anyString);
                times = 0;
            }
        };
    }

    @Test
    public void testDeleteUserNoAdminsLeft() {
        List<UserProfile> lst = new ArrayList<>();
        lst.add(new UserProfile());
        new Expectations() {
            {
                userRepository.search((UserSearchCriteriaData) any);
                result = lst;
            }
        };

        try {
            UserProfile data = new UserProfile();
            data.setUuid(UUID.randomUUID().toString());
            sut.delete(data);

            fail("Should have failed");
        } catch (DataInputException die) {
            MessageData message = die.getFirstMessageData();
            Assert.assertEquals("U008", message.getCode());
        }
    }

    @Test
    public void testDeleteUserSuccess() {
        UserProfile data = new UserProfile();
        data.setUuid(UUID.randomUUID().toString());
        sut.delete(data);

        new Verifications() {
            {
                userCredentialRepository.delete(UserCredential.class, anyString);
                times = 1;

                userRepository.delete(UserProfile.class, anyString);
                times = 1;
            }
        };
    }

    @Test
    public void testChangePasswordUserNotFound() {
        new Expectations() {
            {
                userRepository.search((UserSearchCriteriaData) any);
                result = new ArrayList<>();
            }
        };

        try {
            sut.changePassword("test@gmail.com", "x", "x");
            fail("Should have failed");
        }
        catch (DataInputException die) {
            MessageData message = die.getFirstMessageData();
            Assert.assertEquals("G006", message.getCode());
            System.out.println(die.getMessagesAsString("\\n"));
        }
    }

    @Test
    public void testChangePasswordNewUser() {
        String uuid = UUID.randomUUID().toString();
        UserProfile userProfile = new UserProfile();
        userProfile.setUuid(uuid);
        userProfile.setActiveFlag("Y");

        UserCredential credential = new UserCredential(uuid);
        userProfile.setUserCredential(credential);

        new Expectations() {
            {
                userRepository.search((UserSearchCriteriaData) any);
                result = Arrays.asList(new UserProfile[] { userProfile });

                passwordValidator.encryptPassword(anyString);
                result = "|encrypted|";

                userCredentialRepository.retrieve(UserCredential.class, anyString);
                result = null;
            }
        };

        sut.changePassword("test@gmail.com", "newPass", "x");
        Assert.assertEquals("|encrypted|", userProfile.getUserCredential().getPassword());
        Assert.assertNotNull(userProfile.getUserCredential().getLastModifiedTs());

        new Verifications() {
            {
                userCredentialValidator.assertValidPassword(anyString);
                times = 1;

                passwordValidator.encryptPassword(anyString);
                times = 1;

                userCredentialRepository.add((UserCredential) any);
                times = 1;

                userCredentialRepository.save((UserCredential) any);
                times = 0;
            }
        };
    }

    @Test
    public void testChangePasswordExistingUser() {
        String uuid = UUID.randomUUID().toString();
        UserProfile userProfile = new UserProfile();
        userProfile.setUuid(uuid);
        userProfile.setActiveFlag("Y");

        UserCredential credential = new UserCredential(uuid);
        userProfile.setUserCredential(credential);

        new Expectations() {
            {
                userRepository.search((UserSearchCriteriaData) any);
                result = Arrays.asList(new UserProfile[] { userProfile });

                passwordValidator.encryptPassword(anyString);
                result = "|encrypted|";

                userCredentialRepository.retrieve(UserCredential.class, anyString);
                result = credential;

                passwordValidator.isNotValidPassword(anyString, anyString);
                result = false;
            }
        };

        sut.changePassword("test@gmail.com", "newPass", "x");
        Assert.assertEquals("|encrypted|", userProfile.getUserCredential().getPassword());
        Assert.assertNotNull(userProfile.getUserCredential().getLastModifiedTs());

        new Verifications() {
            {
                userCredentialValidator.assertValidPassword(anyString);
                times = 1;

                passwordValidator.encryptPassword(anyString);
                times = 1;

                userCredentialRepository.add((UserCredential) any);
                times = 0;

                userCredentialRepository.save((UserCredential) any);
                times = 1;
            }
        };
    }

    @Test
    public void testChangePasswordPwNotSupplied() {
        String uuid = UUID.randomUUID().toString();
        UserProfile userProfile = new UserProfile();
        userProfile.setUuid(uuid);
        userProfile.setActiveFlag("Y");

        UserCredential credential = new UserCredential(uuid);
        userProfile.setUserCredential(credential);

        new Expectations() {
            {
                userRepository.search((UserSearchCriteriaData) any);
                result = Arrays.asList(new UserProfile[] { userProfile });
            }
        };

        try {
            sut.changePassword("test@gmail.com", null, null);

            fail("Should have failed");
        }
        catch (DataInputException die) {
            MessageData message = die.getFirstMessageData();
            Assert.assertEquals("G001", message.getCode());
        }
    }

    @Test
    public void testChangePasswordCredUUIDNotSupplied() {
        String uuid = UUID.randomUUID().toString();
        UserProfile userProfile = new UserProfile();
        userProfile.setUuid(uuid);
        userProfile.setActiveFlag("Y");

        UserCredential credential = new UserCredential();
        userProfile.setUserCredential(credential);

        new Expectations() {
            {
                userRepository.search((UserSearchCriteriaData) any);
                result = Arrays.asList(new UserProfile[] { userProfile });
            }
        };

        try {
            sut.changePassword("test@gmail.com", "newpassword", "currentpassword");

            fail("Should have failed");
        }
        catch (DataInputException die) {
            MessageData message = die.getFirstMessageData();
            Assert.assertEquals("G001", message.getCode());
        }
    }

    @Test
    public void testResetPasswordUuidNull() {
        try {
            sut.resetPassword("test@gmail.com", null);
            fail("Should have failed");
        }
        catch (DataInputException die) {
            List<MessageData> messages = die.getMessageData();
            Assert.assertEquals(1, messages.size());
            String code = messages.get(0).getCode();
            Assert.assertEquals("G001", code);
            System.out.println(die.getMessagesAsString("\\n"));
        }
    }

    @Test
    public void testResetPasswordEmailAddressNull() {
        sut.resetPassword(null);

        new Verifications() {
            {
                passwordGeneratorRepository.generate();
                times = 0;

                passwordValidator.encryptPassword(anyString);
                times = 0;

                userCredentialRepository.save((UserCredential) any);
                times = 0;

                emailRepository.send((Properties) any);
                times = 0;
            }
        };
    }

    @Test
    public void testResetPasswordLookupNotFound() {
        new Expectations() {
            {
                lookupKeyValueService.retrieve(any);
                result = null;
            }
        };
        try {
            sut.resetPassword("test@gmail.com", UUID.randomUUID().toString());
            fail("Should have failed");
        }
        catch (DataInputException die) {
            List<MessageData> messages = die.getMessageData();
            Assert.assertEquals(1, messages.size());
            String code = messages.get(0).getCode();
            Assert.assertEquals("U006", code);
            System.out.println(die.getMessagesAsString("\\n"));
        }
    }

    @Test
    public void testResetPasswordWithUuidSuccess() {
        new Expectations() {
            {
                lookupKeyValueService.retrieve(any);
                result = new LookupKeyValue();

                userRepository.search((UserSearchCriteriaData) any);

                String uuid = UUID.randomUUID().toString();
                UserProfile userProfile = new UserProfile();
                userProfile.setUuid(uuid);
                userProfile.setActiveFlag("Y");
                userProfile.setEmailAddress("test@gmail.com");

                UserCredential credential = new UserCredential(uuid);
                userProfile.setUserCredential(credential);

                result = Arrays.asList(new UserProfile[] { userProfile });
            }
        };

        sut.resetPassword("test@gmail.com", UUID.randomUUID().toString());

        new Verifications() {
            {
                lookupKeyValueService.delete((LookupKeyValue) any);
                times = 1;

                passwordGeneratorRepository.generate();
                times = 1;

                passwordValidator.encryptPassword(anyString);
                times = 1;

                userCredentialRepository.save((UserCredential) any);
                times = 1;

                emailRepository.send((Properties) any);
                times = 1;
            }
        };
    }

    @Test
    public void testResetPasswordEmailAddressNotFound() {
        new Expectations() {
            {
                userRepository.search((UserSearchCriteriaData) any);
                result = new ArrayList<>();
            }
        };

        try {
            sut.resetPassword("test@gmail.com");
            fail("Should have failed");
        }
        catch (DataInputException die) {
            List<MessageData> messages = die.getMessageData();
            Assert.assertEquals(1, messages.size());
            String code = messages.get(0).getCode();
            Assert.assertEquals("U005", code);
            System.out.println(die.getMessagesAsString("\\n"));
        }
    }

    @Test
    public void testResetConfirmationEmailAddressNull() {
        try {
            sut.sendResetConfirmation(null);
            fail("Should have failed");
        }
        catch (DataInputException die) {
            List<MessageData> messages = die.getMessageData();
            Assert.assertEquals(1, messages.size());
            String code = messages.get(0).getCode();
            Assert.assertEquals("G001", code);
            System.out.println(die.getMessagesAsString("\\n"));
        }
    }

    @Test
    public void testResetConfirmationEmailAddressBlank() {
        try {
            sut.sendResetConfirmation("");
            fail("Should have failed");
        }
        catch (DataInputException die) {
            List<MessageData> messages = die.getMessageData();
            Assert.assertEquals(1, messages.size());
            String code = messages.get(0).getCode();
            Assert.assertEquals("G001", code);
            System.out.println(die.getMessagesAsString("\\n"));
        }
    }

    @Test
    public void testResetConfirmationEmailAddressSupplied() {
        new Expectations() {
            {
                userRepository.search((UserSearchCriteriaData) any);
                result = new ArrayList<>();
            }
        };

        try {
            sut.sendResetConfirmation("test@gmail.com");
        }
        catch (DataInputException die) {
            List<MessageData> messages = die.getMessageData();
            Assert.assertEquals(1, messages.size());
            String code = messages.get(0).getCode();
            Assert.assertEquals("G003", code);
            System.out.println(die.getMessagesAsString("\\n"));
        }

        new Verifications() {
            {
                lookupKeyValueService.add((LookupKeyValue) any);
                times = 0;

                emailRepository.send((Properties) any);
                times = 0;
            }
        };
    }

    @Test
    public void testResetConfirmationSuccess() {
        new Expectations() {
            {
                userRepository.search((UserSearchCriteriaData) any);
                String uuid = UUID.randomUUID().toString();
                UserProfile userProfile = new UserProfile();
                userProfile.setUuid(uuid);
                userProfile.setActiveFlag("Y");
                userProfile.setEmailAddress("test@gmail.com");

                result = Arrays.asList(new UserProfile[] { userProfile });
            }
        };

        sut.sendResetConfirmation("test@gmail.com");

        new Verifications() {
            {
                lookupKeyValueService.add((LookupKeyValue) any);
                times = 1;

                emailRepository.send((Properties) any);
                times = 1;
            }
        };
    }

    @Test
    public void testRetrieveAndSearch() {
        String uuid = UUID.randomUUID().toString();
        sut.retrieve(uuid);

        new Verifications() {
            {
                userRepository.retrieve(UserProfile.class, uuid);
                times = 1;
            }
        };

        sut.search(new UserSearchCriteriaData());

        new Verifications() {
            {
                userRepository.search((UserSearchCriteriaData) any);
                times = 1;
            }
        };
    }
}
