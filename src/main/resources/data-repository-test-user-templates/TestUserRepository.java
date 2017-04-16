package com.${companyName}.${productName}.model.repository.user;

import com.${companyName}.${productName}.model.criteria.UserSearchCriteriaData;
import com.${companyName}.${productName}.model.data.UserCredential;
import com.${companyName}.${productName}.model.data.UserProfile;
import com.${companyName}.${productName}.model.enumeration.Role;
import com.${companyName}.${productName}.model.repository.TriadTestCase;
import com.${companyName}.${productName}.model.repository.user.UserCredentialRepository;
import com.${companyName}.${productName}.model.repository.user.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestUserRepository extends TriadTestCase {
    private static final String DEFAULT_PASSWORD = "BigKahunaBurger2017";

    @Resource(name = "UserRepository")
    private UserRepository userRepository;

    @Resource(name = "UserCredentialRepository")
    private UserCredentialRepository userCredentialRepository;

    public TestUserRepository() {
    }

    private UserProfile createUserProfile(String uuid) {
        final UserProfile user = new UserProfile();
        user.setUuid(uuid);
        user.setEmailAddress("me" + System.nanoTime() + "@gmail.com");
        user.setFirstName("John");
        user.setLastName("Wayne");
        user.setCreateTs(new Timestamp(System.currentTimeMillis()));
        user.setLastLoggedTs(new Timestamp(System.currentTimeMillis()));
        user.setLastModifiedTs(new Timestamp(System.currentTimeMillis()));
        user.setRole(Role.ADMIN);
        user.setActiveFlag("Y");
        return user;
    }

    @Test
    public void testAddUpdateRetrieveDelete() {
        try {
            String uuid = UUID.randomUUID().toString();
            final UserProfile user = createUserProfile(uuid);
            userRepository.add(user);

            UserProfile retr = userRepository.retrieve(UserProfile.class, uuid);
            Assert.assertNotNull(retr);

            user.setEmailAddress("you@gmail.com");
            userRepository.save(user);
            retr = userRepository.retrieve(UserProfile.class, uuid);
            Assert.assertNotNull(retr);
            Assert.assertEquals("you@gmail.com", retr.getEmailAddress());

            assertCredentials(uuid, user.getEmailAddress());

            userRepository.delete(UserProfile.class, uuid);
            retr = userRepository.retrieve(UserProfile.class, uuid);
            Assert.assertNull(retr);
        } catch (final Throwable t) {
            t.printStackTrace();
            fail(t.toString());
        }
    }

    @Test
    public void testSearchNoCriteria() {
        String uuid = UUID.randomUUID().toString();
        final UserProfile user = createUserProfile(uuid);
        userRepository.add(user);

        List<UserProfile> users = userRepository.search(null);
        Assert.assertTrue(users.size() > 0);
    }

    @Test
    public void testSearchByUuid() {
        List<UserProfile> users = userRepository.search(null);
        if (users.size() == 0) {
            return;
        }

        String uuid = users.get(0).getUuid();

        UserSearchCriteriaData criteria = new UserSearchCriteriaData();
        criteria.setUuid(uuid);
        users = userRepository.search(criteria);
        Assert.assertEquals(1, users.size());
    }

    @Test
    public void testSearchByUuids() {
        List<UserProfile> users = userRepository.search(null);
        int size = users.size();
        if (size == 0) {
            return;
        }

        List<String> uuids = new ArrayList<>();
        for (UserProfile user : users) {
            uuids.add(user.getUuid());
        }

        UserSearchCriteriaData criteria = new UserSearchCriteriaData();
        criteria.setUuids(uuids);
        users = userRepository.search(criteria);
        Assert.assertEquals(size, users.size());
    }

    @Test
    public void testSearchByActiveAndRole() {
        UserSearchCriteriaData criteria = new UserSearchCriteriaData();
        criteria.setActiveFlag("Y");
        criteria.setRole(Role.ADMIN);
        List<UserProfile> users = userRepository.search(criteria);
        Assert.assertNotNull(users);
    }

    @Test
    public void testSearchByName() {
        List<UserProfile> users;

        String uuid = UUID.randomUUID().toString();
        final UserProfile user = createUserProfile(uuid);
        user.setFirstName("TestSearchUser");
        user.setLastName("TestSearchLastName");
        userRepository.add(user);

        UserSearchCriteriaData criteria = new UserSearchCriteriaData();
        criteria.setFirstName("TestSearchUser");
        criteria.setLastName("TestSearchLastName");

        users = userRepository.search(criteria);
        Assert.assertTrue(users.size() > 0);

        //Make sure only matching names were returned
        for (UserProfile usr : users) {
            Assert.assertTrue(usr.getFirstName().startsWith("TestSearchUser"));
            Assert.assertTrue(usr.getLastName().startsWith("TestSearchLastName"));
        }
    }

    @Test
    public void testSearchByPartialName() {
        List<UserProfile> users;

        String uuid = UUID.randomUUID().toString();
        final UserProfile user = createUserProfile(uuid);
        user.setFirstName("TestSearchUser2");
        user.setLastName("TestSearchLastName2");
        userRepository.add(user);

        UserSearchCriteriaData criteria = new UserSearchCriteriaData();
        criteria.setFirstName("Tes");

        users = userRepository.search(criteria);
        Assert.assertTrue(users.size() > 0);

        //Make sure only matching names were returned
        for (UserProfile usr : users) {
            Assert.assertTrue(usr.getFirstName().startsWith("Tes"));
        }
    }



    private void assertCredentials(String uuid, String emailAddress) {
        final UserCredential bean = new UserCredential(uuid);
        bean.setPassword(DEFAULT_PASSWORD);
        bean.setLastModifiedTs(new Timestamp(System.currentTimeMillis()));
        userCredentialRepository.add(bean);

        UserSearchCriteriaData criteria = new UserSearchCriteriaData();
        criteria.setEmailAddress(emailAddress);
        List<UserProfile> results = userRepository.search(criteria);
        Assert.assertEquals(1, results.size());

        final UserCredential userCredential = userCredentialRepository.retrieve(UserCredential.class, uuid);
        assertNotNull(userCredential);
        assertTrue(userCredential.getPassword().equals(DEFAULT_PASSWORD));
        assertNotNull(userCredential.getLastModifiedTs());

        userCredential.setPassword("blah");
        bean.setLastModifiedTs(new Timestamp(System.currentTimeMillis()));
        userCredentialRepository.changePassword(userCredential);
        final UserCredential newPass = userCredentialRepository.retrieve(UserCredential.class, uuid);
        assertNotNull(newPass);
        assertTrue(newPass.getPassword().equals("blah"));
        assertNotNull(userCredential.getLastModifiedTs());

        userCredentialRepository.delete(UserCredential.class, uuid);
        final UserCredential goner = userCredentialRepository.retrieve(UserCredential.class, uuid);
        assertNull(goner);
    }
}
