package com.${companyName}.${productName}.model.repository.user;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

public class TestPasswordGeneratorRepository extends TestCase {
    private static final String ONE_UPPER = "^(?=.*[A-Z]).+\$";
    private static final String ONE_NUMBER = "^(?=.*\\d).+\$";

    @Test
    public void testGenerate() {
        PasswordGeneratorRepository passwordGenerator = new PasswordGeneratorRepositoryImpl();
        final String password = passwordGenerator.generate();
        Assert.assertTrue("Missing Uppercase Letter", password.matches(ONE_UPPER));
        Assert.assertTrue("Missing number", password.matches(ONE_NUMBER));
        System.out.println(password);
    }
}
