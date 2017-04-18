package com.${companyName}.${productName}.model.repository.mail;

import java.util.Properties;

/**
 * Defines a class which sends emails.
 *
 * @author ${codeAuthor}.
 */
public interface MailRepository {
    /**
     * The property name containing the SMTP host URL.
     */
    String PROP_HOST = "mail.host";
    /**
     * The property name containing the email address sending the email.
     */
    String PROP_FROM = "mail.from";
    /**
     * The property name containing the email address receiving the email.
     */
    String PROP_TO = "mail.to";
    /**
     * The property name containing the subject of the email.
     */
    String PROP_SUBJECT = "mail.subject";
    /**
     * The property name containing the body text of the email.
     */
    String PROP_BODY = "mail.body";

    /**
     * Sends an email using the supplied properties.
     *
     * @param properties the properties.  Must contain the host, the from address, the to address, the subject, and the
     *                   body of the email.
     */
    void send(Properties properties);

    /**
     * Sends an email using the supplied properties.
     *
     * @param properties     the properties.  Must contain the host, the from address, the to address, the subject, and
     *                       the body of the email.
     * @param fileName       the file name pointing to the attachment
     * @param attachmentName the name to assign to the attachment.
     */
    void send(Properties properties, String fileName, String attachmentName);
}
