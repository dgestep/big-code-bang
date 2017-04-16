package com.${companyName}.${productName}.model.repository.user;

import com.${companyName}.${productName}.model.data.MessageData;
import com.${companyName}.${productName}.model.exception.DataInputException;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestUserCredentialValidator extends TestCase {
    private UserCredentialValidatorImpl sut;

    @Before
    @Override
    public void setUp() {
        sut = new UserCredentialValidatorImpl();
    }

    @Test
    public void testPasswordMinLength() {
        try {
            sut.assertValidPassword("1");
            fail("Should have failed");
        }
        catch (DataInputException die) {
            MessageData message = die.getFirstMessageData();
            Assert.assertEquals("U004", message.getCode());
            System.out.println(message);
        }
    }

    @Test
    public void testPasswordNoNumber() {
        try {
            sut.assertValidPassword("abcdeFGH");
            fail("Should have failed");
        }
        catch (DataInputException die) {
            MessageData message = die.getFirstMessageData();
            Assert.assertEquals("U004", message.getCode());
            System.out.println(message);
        }
    }

    @Test
    public void testPasswordNoCapitalLetter() {
        try {
            sut.assertValidPassword("abcdefg1234");
            fail("Should have failed");
        }
        catch (DataInputException die) {
            MessageData message = die.getFirstMessageData();
            Assert.assertEquals("U004", message.getCode());
            System.out.println(message);
        }
    }

    @Test
    public void testSuccessfulPassword() {
        PasswordGeneratorRepository passwordGenerator = new PasswordGeneratorRepositoryImpl();
        final String password = passwordGenerator.generate();
        sut.assertValidPassword(password);

        System.out.println("Success password: " + password);
    }
}
