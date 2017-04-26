package ${topLevelDomain}.${companyName}.${productName}.model.exception;

import ${topLevelDomain}.${companyName}.${productName}.model.log.LogFactory;
import ${topLevelDomain}.${companyName}.${productName}.model.log.Logger;

/**
 * A runtime exception representing any unrecoverable exception generated within the Model packages.
 *
 * @author ${codeAuthor}.
 */
public class SystemLoggedException extends RuntimeException {
    private static final long serialVersionUID = 7272009;
    private String code;
    private boolean logged;

    /**
     * Creates an instance of this exception.
     */
    public SystemLoggedException() {
        super();
        log(this);
    }

    /**
     * Creates an instance of this exception.
     *
     * @param message the message.
     */
    public SystemLoggedException(final String message) {
        super(message);
        log(this);
    }

    /**
     * Creates an instance of this exception.
     *
     * @param message the error message.
     * @param code    the code associated with this exception.
     */
    public SystemLoggedException(final String message, final String code) { // NOCHECKSTYLE
        super(message);
        setCode(code);

        log(message, this);
    }

    /**
     * Creates an instance of this exception.
     *
     * @param message the message.
     * @param thr     the {@link Throwable} associated with this exception.
     */
    public SystemLoggedException(final String message, final Throwable thr) {
        super(message, thr);

        log(message, thr);
    }

    /**
     * Creates an instance of this exception.
     *
     * @param message the message.
     * @param thr     the {@link Throwable} associated with this exception.
     * @param code    the code associated with this exception.
     */
    public SystemLoggedException(final String message, final Throwable thr, final String code) { // NOCHECKSTYLE
        super(message, thr);
        setCode(code);

        log(message, thr);
    }

    /**
     * Creates an instance of this exception.
     *
     * @param thr the {@link Throwable} associated with this exception.
     */
    public SystemLoggedException(final Throwable thr) {
        super(thr);

        log(thr);
    }

    /**
     * Creates an instance of this exception.
     *
     * @param thr  the originating Throwable.
     * @param code the code associated with this exception.
     */
    public SystemLoggedException(final Throwable thr, final String code) { // NOCHECKSTYLE
        super(thr);
        setCode(code);

        log(thr);
    }

    /**
     * Logs the supplied exception.
     *
     * @param t the exception.
     */
    private void log(final Throwable t) {
        log(t.getMessage(), t);
    }

    /**
     * Logs the supplied message and exception.
     *
     * @param message the message.
     * @param t       the exception.
     */
    private void log(final String message, final Throwable t) {
        final Logger logger = LogFactory.getLogger();
        logger.error(message, t);
    }

    /**
     * Returns the error code associated with this exception.
     *
     * @return the code.
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets an error code associated with this exception.
     *
     * @param code the code.
     */
    public void setCode(final String code) { // NOCHECKSTYLE
        this.code = code;
    }

    /**
     * Returns true if the client that caught this exception has already logged the stack trace.
     *
     * @return true if the exception has been logged.
     */
    public boolean isLogged() {
        return logged;
    }

    /**
     * Set to true to indicate that the client that caught this exception has already logged the stack trace.
     *
     * @param logged true if logged.
     */
    public void setLogged(final boolean logged) { // NOCHECKSTYLE
        this.logged = logged;
    }
}
