package com.${companyName}.${productName}.model.exception;

import com.${companyName}.${productName}.model.log.LogFactory;
import com.${companyName}.${productName}.model.log.Logger;
import junit.framework.TestCase;
import mockit.Expectations;
import mockit.Mocked;
import mockit.Tested;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMockit.class)
public class TestSystemLoggedException extends TestCase {

    @Mocked
    private LogFactory logFactory;

    @Mocked
    private Logger logger;

    @Tested
    private SystemLoggedException sut;

    @Before
    @Override
    public void setUp() {
        new Expectations() {
            {
                LogFactory.getLogger();
                result = logger;
            }
        };
    }

    @Test
    public void testEmptyConstructor() {
        sut = new SystemLoggedException();

        new Verifications() {
            {
                logger.error(null, sut);
                times = 1;
            }
        };
    }

    @Test
    public void testConstructWithMessage() {
        sut = new SystemLoggedException("failed");

        new Verifications() {
            {
                logger.error("failed", sut);
                times = 1;
            }
        };
    }

    @Test
    public void testConstructWithMessageAndCode() {
        sut = new SystemLoggedException("failed", "500");

        new Verifications() {
            {
                logger.error("failed", sut);
                times = 1;

                sut.setCode("500");
                times = 1;
            }
        };
    }

    @Test
    public void testConstructWithMessageAndThrowable() {
        Throwable thr = new RuntimeException("I want to fail");
        sut = new SystemLoggedException("failed", thr);

        new Verifications() {
            {
                logger.error("failed", thr);
                times = 1;
            }
        };
    }

    @Test
    public void testConstructWithMessageThrowableAndCode() {
        Throwable thr = new RuntimeException("I want to fail");
        sut = new SystemLoggedException("failed", thr, "500");

        new Verifications() {
            {
                logger.error("failed", thr);
                times = 1;

                sut.setCode("500");
                times = 1;
            }
        };
    }

    @Test
    public void testConstructWithThrowable() {
        Throwable thr = new RuntimeException("I want to fail");
        sut = new SystemLoggedException(thr);

        new Verifications() {
            {
                logger.error(thr.getMessage(), thr);
                times = 1;
            }
        };
    }

    @Test
    public void testConstructWithThrowableAndCode() {
        Throwable thr = new RuntimeException("I want to fail");
        sut = new SystemLoggedException(thr, "500");

        new Verifications() {
            {
                logger.error(thr.getMessage(), thr);
                times = 1;

                sut.setCode("500");
                times = 1;

                sut.setLogged(true);
                times = 1;
            }
        };

        Assert.assertTrue(sut.isLogged());
        Assert.assertEquals("500", sut.getCode());
    }
}
