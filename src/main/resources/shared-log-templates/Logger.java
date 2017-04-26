package ${topLevelDomain}.${companyName}.${productName}.model.log;

/**
 * Defines a class which performs logging.
 *
 * @author ${codeAuthor}.
 */
@SuppressWarnings("PMD.TooManyMethods")
public interface Logger {
    /**
     * Returns the assigned logger name.
     *
     * @return the name.
     */
    String getLoggerName();

    /**
     * Logs the supplied message at the Trace level.
     *
     * @param message the message.
     */
    void trace(Object message);

    /**
     * Logs the supplied message at the Trace level.
     *
     * @param message   the message.
     * @param throwable the {@link Throwable} used to log the stack trace.
     */
    void trace(Object message, Throwable throwable);

    /**
     * Logs the supplied message at the Debug level.
     *
     * @param message the message.
     */
    void debug(Object message);

    /**
     * Logs the supplied message at the Debug level.
     *
     * @param message   the message.
     * @param throwable the {@link Throwable} used to log the stack trace.
     */
    void debug(Object message, Throwable throwable);

    /**
     * Logs the supplied message at the Info level.
     *
     * @param message the message.
     */
    void info(Object message);

    /**
     * Logs the supplied message at the Info level.
     *
     * @param message   the message.
     * @param throwable the {@link Throwable} used to log the stack trace.
     */
    void info(Object message, Throwable throwable);

    /**
     * Logs the supplied message at the Warn level.
     *
     * @param message the message.
     */
    void warn(Object message);

    /**
     * Logs the supplied message at the Warn level.
     *
     * @param message   the message.
     * @param throwable the {@link Throwable} used to log the stack trace.
     */
    void warn(Object message, Throwable throwable);

    /**
     * Logs the supplied message at the Error level.
     *
     * @param message the message.
     */
    void error(Object message);

    /**
     * Logs the supplied message at the Error level.
     *
     * @param message   the message.
     * @param throwable the {@link Throwable} used to log the stack trace.
     */
    void error(Object message, Throwable throwable);

    /**
     * Logs the supplied message at the Fatal level.
     *
     * @param message the message.
     */
    void fatal(Object message);

    /**
     * Logs the supplied message at the Fatal level.
     *
     * @param message   the message.
     * @param throwable the {@link Throwable} used to log the stack trace.
     */
    void fatal(Object message, Throwable throwable);

    /**
     * Allows the caller to see if the system currently allows messages at the DEBUG level.
     *
     * @return true if allowed.
     */
    boolean isDebugEnabled();

    /**
     * Allows the caller to see if the system currently allows messages at the INFO level.
     *
     * @return true if allowed.
     */
    boolean isInfoEnabled();

    /**
     * Allows the caller to see if the system currently allows messages at the WARN level.
     *
     * @return true if allowed.
     */
    boolean isWarnEnabled();

    /**
     * Allows the caller to see if the system currently allows messages at the ERROR level.
     *
     * @return true if allowed.
     */
    boolean isErrorEnabled();
}
