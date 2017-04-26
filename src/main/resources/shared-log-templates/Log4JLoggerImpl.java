package ${topLevelDomain}.${companyName}.${productName}.model.log;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Level;

/**
 * A {@link Logger} backed by the Log4j library.
 *
 * @author ${codeAuthor}.
 */
@SuppressWarnings("PMD.TooManyMethods")
public class Log4JLoggerImpl implements Logger {
    private final org.apache.log4j.Logger log;

    /**
     * Creates a Log4JLoggerImpl that wraps Log4J's Logger and uses the stack to figure out what the calling class
     * should be.
     *
     * @param stackLocation the location of the class within the stack trace where logging was requested.
     * @throws IllegalArgumentException if the stackLocation is 0 or less.
     */
    public Log4JLoggerImpl(final int stackLocation) throws IllegalArgumentException {
        validateArgument(stackLocation);
        final String clzName = getClassNameAtStackLocation(stackLocation);
        log = org.apache.log4j.Logger.getLogger(clzName);
    }

    /**
     * Creates a Log4JLoggerImpl that wraps Log4J's Logger and uses name parameter
     *
     * @param name the name to assign to the logger.
     * @throws IllegalArgumentException if the name is null or zero length.
     */
    public Log4JLoggerImpl(final String name) throws IllegalArgumentException {
        validateArgument(name);

        log = org.apache.log4j.Logger.getLogger(name);
    }

    /**
     * Evaluates the stack trace and returns the class name associated at the supplied location in the stack.
     *
     * @param stackLocation the location of the class within the stack trace where logging was requested.
     * @return the class name.
     */
    private String getClassNameAtStackLocation(final int stackLocation) {
        final int newLevel = stackLocation + 2;
        final Throwable t = new Throwable();
        final StackTraceElement[] stackTrace = t.getStackTrace();
        final StackTraceElement element = stackTrace[newLevel];

        if ("LogFactory".equals(element.getClassName())) {
            return stackTrace[newLevel + 1].getClassName();
        } else {
            return stackTrace[newLevel].getClassName();
        }
    }

    /**
     * Assets the stack location is a valid location.
     *
     * @param stackLocation the location of the class within the stack trace where logging was requested.
     */
    private void validateArgument(final int stackLocation) {
        if (stackLocation < 1) {
            throw new IllegalArgumentException("stackLocation must be greater than 0");
        }
    }

    /**
     * Assets the supplied logger name is a valid name.
     *
     * @param name the logger name.
     */
    private void validateArgument(final String name) {
        if (StringUtils.isEmpty(name)) {
            throw new IllegalArgumentException("A valid is required for the logger name");
        }
    }

    @Override
    public String getLoggerName() {
        return log.getName();
    }

    @Override
    public void debug(final Object message) {
        if (log.isDebugEnabled()) {
            log.debug(message);
        }
    }

    @Override
    public void debug(final Object message, final Throwable throwable) {
        if (log.isDebugEnabled()) {
            log.debug(message, throwable);
        }
    }

    @Override
    public void error(final Object message) {
        if (message instanceof Throwable) {
            log.error("", (Throwable) message);
        } else {
            log.error(message);
        }
    }

    @Override
    public void error(final Object message, final Throwable t) {
        log.error(message, t);
    }

    @Override
    public void fatal(final Object message) {
        log.fatal(message);
    }

    @Override
    public void fatal(final Object message, final Throwable t) {
        log.fatal(message, t);
    }

    @Override
    public void trace(final Object message) {
        if (log.isTraceEnabled()) {
            log.trace(message);
        }
    }

    @Override
    public void trace(final Object message, final Throwable t) {
        if (log.isTraceEnabled()) {
            log.trace(message, t);
        }
    }

    @Override
    public void warn(final Object message) {
        log.warn(message);
    }

    @Override
    public void warn(final Object message, final Throwable throwable) {
        log.warn(message, throwable);
    }

    @Override
    public void info(final Object message) {
        if (log.isInfoEnabled()) {
            log.info(message);
        }
    }

    @Override
    public void info(final Object message, final Throwable throwable) {
        if (log.isInfoEnabled()) {
            log.info(message, throwable);
        }
    }

    @Override
    public boolean isDebugEnabled() {
        return this.log.isEnabledFor(Level.DEBUG);
    }

    @Override
    public boolean isErrorEnabled() {
        return this.log.isEnabledFor(Level.ERROR);
    }

    @Override
    public boolean isInfoEnabled() {
        return this.log.isEnabledFor(Level.INFO);
    }

    @Override
    public boolean isWarnEnabled() {
        return this.log.isEnabledFor(Level.WARN);
    }
}
