package ${topLevelDomain}.${companyName}.${productName}.model;

/**
 * Houses configuration data.
 *
 * @author ${codeAuthor}.
 */
public final class ConfigConstant {
    /**
     * Maximum days a request request will remain active before being purged.
     */
    public static final int RESET_PASSWORD_MAX_DAYS = 1;

    /**
     * Emails sent use this FROM address.
     */
    public static final String EMAIL_FROM_ADDRESS = "${mailFromAddress}";

    /**
     * The subject of a password reset email.
     */
    public static final String EMAIL_RESET_PASSWORD_SUBJECT = "password reset request";

    /**
     * The body of a password reset email.
     */
    public static final String EMAIL_RESET_PASSWORD_CONFIRMATION_BODY = "A request has been made to reset your password"
            + " for the ${applicationTitle} application. If you did not make this request or you do "
            + "not want to reset your password then ignore this email. This link will be inactivated after you reset "
            + "your password or if you have not reset your password within 24 hours. %s";

    /**
     * The body of a password reset email.
     */
    public static final String EMAIL_RESET_PASSWORD_BODY = "Your password for the ${applicationTitle} "
            + "application has been reset. Your new password is ( %s ), minus the surrounding parenthesis. Please "
            + "return to the ${applicationTitle} application to login. ";

    /**
     * The email server host address.
     */
    public static final String EMAIL_HOST = "${mailHostName}"; //NOPMD

    /**
     * The email server host address for local.
     */
    public static final String EMAIL_PASSWORD_LOCAL_RESET_URL =
            "http://localhost${localhostViewPort}/app-confirm-password-reset?email=%s&uuid=%s";

    /**
     * The email server host address for production.
     */
    public static final String EMAIL_PASSWORD_PROD_RESET_URL =
            "http://${serverHostName}${serverViewPort}/app-confirm-password-reset?email=%s&uuid=%s";

    /**
     * Default constructor.
     */
    private ConfigConstant() {
    }
}
