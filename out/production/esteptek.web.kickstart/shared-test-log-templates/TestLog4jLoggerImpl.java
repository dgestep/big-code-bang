package com.${companyName}.${productName}.model.log;

import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;
import org.apache.log4j.Level;
import org.apache.log4j.Priority;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMockit.class)
public class TestLog4jLoggerImpl {
    @Mocked
    private org.apache.log4j.Logger logger;

    private Log4JLoggerImpl sut;

    @Test
    public void testConstructWithStackLocation() {
        sut = new Log4JLoggerImpl(1);

        new Verifications() {
            {
                org.apache.log4j.Logger.getLogger("sun.reflect.NativeMethodAccessorImpl");
                times = 1;
            }
        };
    }

    @Test
    public void testConstructWithInvalidStackLocation() {
        try {
            sut = new Log4JLoggerImpl(-1);

            Assert.fail("Should have failed");
        } catch (IllegalArgumentException iae) {
            System.out.println(iae.getMessage());
        }
    }

    @Test
    public void testConstructWithName() {
        new Expectations() {
            {
                logger.getName();
                result = "myLogger";
            }
        };

        sut = new Log4JLoggerImpl("mylogger");

        new Verifications() {
            {
                org.apache.log4j.Logger.getLogger("mylogger");
                times = 1;
            }
        };

        Assert.assertEquals("myLogger", sut.getLoggerName());
    }

    @Test
    public void testConstructWithInvalidName() {
        try {
            sut = new Log4JLoggerImpl(null);

            Assert.fail("Should have failed");
        } catch (IllegalArgumentException iae) {
            System.out.println(iae.getMessage());
        }
    }

    @Test
    public void testDebug() {
        new Expectations() {
            {
                logger.isDebugEnabled();
                result = true;
            }
        };

        sut = new Log4JLoggerImpl("mylogger");
        sut.debug("test");

        new Verifications() {
            {
                logger.debug("test");
                times = 1;
            }
        };
    }

    @Test
    public void testDebugWithException() {
        new Expectations() {
            {
                logger.isDebugEnabled();
                result = true;
            }
        };

        sut = new Log4JLoggerImpl("mylogger");
        sut.debug("test", new RuntimeException("fail me"));

        new Verifications() {
            {
                logger.debug("test", (Throwable) any);
                times = 1;
            }
        };
    }

    @Test
    public void testDebugNotSet() {
        new Expectations() {
            {
                logger.isDebugEnabled();
                result = false;
            }
        };

        sut = new Log4JLoggerImpl("mylogger");
        sut.debug("test");

        new Verifications() {
            {
                logger.debug("test");
                times = 0;
            }
        };
    }

    @Test
    public void testTrace() {
        new Expectations() {
            {
                logger.isTraceEnabled();
                result = true;
            }
        };

        sut = new Log4JLoggerImpl("mylogger");
        sut.trace("test");

        new Verifications() {
            {
                logger.trace("test");
                times = 1;
            }
        };
    }

    @Test
    public void testTraceWithException() {
        new Expectations() {
            {
                logger.isTraceEnabled();
                result = true;
            }
        };

        sut = new Log4JLoggerImpl("mylogger");
        sut.trace("test", new RuntimeException("fail me"));

        new Verifications() {
            {
                logger.trace("test", (Throwable) any);
                times = 1;
            }
        };
    }

    @Test
    public void testTraceNotSet() {
        new Expectations() {
            {
                logger.isTraceEnabled();
                result = false;
            }
        };

        sut = new Log4JLoggerImpl("mylogger");
        sut.trace("test");

        new Verifications() {
            {
                logger.trace("test");
                times = 0;
            }
        };
    }

    @Test
    public void testWarn() {
        sut = new Log4JLoggerImpl("mylogger");
        sut.warn("test");

        new Verifications() {
            {
                logger.warn("test");
                times = 1;
            }
        };
    }

    @Test
    public void testWarnWithException() {
        sut = new Log4JLoggerImpl("mylogger");
        sut.warn("test", new RuntimeException("fail me"));

        new Verifications() {
            {
                logger.warn("test", (Throwable) any);
                times = 1;
            }
        };
    }

    @Test
    public void testError() {
        sut = new Log4JLoggerImpl("mylogger");
        sut.error("test");

        new Verifications() {
            {
                logger.error("test");
                times = 1;
            }
        };
    }

    @Test
    public void testErrorPassingException() {
        sut = new Log4JLoggerImpl("mylogger");

        Throwable thr = new Throwable("boom");
        sut.error(thr);

        new Verifications() {
            {
                logger.error("", thr);
                times = 1;
            }
        };
    }

    @Test
    public void testErrorWithException() {
        sut = new Log4JLoggerImpl("mylogger");
        sut.error("test", new RuntimeException("fail me"));

        new Verifications() {
            {
                logger.error("test", (Throwable) any);
                times = 1;
            }
        };
    }

    @Test
    public void testInfo() {
        new Expectations() {
            {
                logger.isInfoEnabled();
                result = true;
            }
        };

        sut = new Log4JLoggerImpl("mylogger");
        sut.info("test");

        new Verifications() {
            {
                logger.info("test");
                times = 1;
            }
        };
    }

    @Test
    public void testInfoWithException() {
        new Expectations() {
            {
                logger.isInfoEnabled();
                result = true;
            }
        };

        sut = new Log4JLoggerImpl("mylogger");
        sut.info("test", new RuntimeException("fail me"));

        new Verifications() {
            {
                logger.info("test", (Throwable) any);
                times = 1;
            }
        };
    }

    @Test
    public void testInfoNotSet() {
        new Expectations() {
            {
                logger.isInfoEnabled();
                result = false;
            }
        };

        sut = new Log4JLoggerImpl("mylogger");
        sut.info("test");

        new Verifications() {
            {
                logger.info("test");
                times = 0;
            }
        };
    }


    @Test
    public void testFatal() {
        sut = new Log4JLoggerImpl("mylogger");
        sut.fatal("test");

        new Verifications() {
            {
                logger.fatal("test");
                times = 1;
            }
        };
    }

    @Test
    public void testFatalWithException() {
        sut = new Log4JLoggerImpl("mylogger");
        sut.fatal("test", new RuntimeException("fail me"));

        new Verifications() {
            {
                logger.fatal("test", (Throwable) any);
                times = 1;
            }
        };
    }

    @Test
    public void testEnabledFlags() {
        sut = new Log4JLoggerImpl("mylogger");
        sut.isDebugEnabled();
        sut.isErrorEnabled();
        sut.isInfoEnabled();
        sut.isWarnEnabled();

        new Verifications() {
            {
                logger.isEnabledFor(Level.DEBUG);
                times = 1;

                logger.isEnabledFor(Level.ERROR);
                times = 1;

                logger.isEnabledFor(Level.INFO);
                times = 1;

                logger.isEnabledFor(Level.WARN);
                times = 1;
            }
        };
    }
}
