package com.${companyName}.${productName}.model;

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
    public static final String EMAIL_FROM_ADDRESS = "info@udri.udayton.edu";

    /**
     * The subject of a password reset email.
     */
    public static final String EMAIL_RESET_PASSWORD_SUBJECT = "password reset request";

    /**
     * The body of a password reset email.
     */
    public static final String EMAIL_RESET_PASSWORD_CONFIRMATION_BODY = "A request has been made to reset your password"
            + " for the Hypersonic Materials and Structures application. If you did not make this request or you do "
            + "not want to reset your password then ignore this email. This link will be inactivated after you reset "
            + "your password or if you have not reset your password within 24 hours. %s";

    /**
     * The body of a password reset email.
     */
    public static final String EMAIL_RESET_PASSWORD_BODY = "Your password for Hypersonic Materials and Structures "
            + "application has been reset. Your new password is ( %s ), minus the surrounding parenthesis. Please "
            + "return to Hypersonic Materials and Structures application to login.  You can change your password "
            + "after logging in to the Hypersonic Materials and Structures application by going to your profile "
            + "page and clicking the Password button.";

    /**
     * The email server host address.
     */
    //    public static final String EMAIL_HOST = "localhost";

    public static final String EMAIL_HOST = "10.110.1.193"; //NOPMD - will address later.

    /**
     * The email server host address.
     */
    public static final String EMAIL_PASSWORD_RESET_URL = "http://localhost:8080/hsm/catch-reset?email=%s&lookupkey=%s";

    /**
     * Default constructor.
     */
    private ConfigConstant() {
    }
}
