package ${topLevelDomain}.${companyName}.${productName}.model.repository.mail;

import ${topLevelDomain}.${companyName}.${productName}.model.exception.SystemLoggedException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Repository;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Properties;

/**
 * Sends emails.
 *
 * @author ${codeAuthor}.
 */
@Repository("MailRepository")
public class MailRepositoryImpl implements MailRepository {
    @Override
    public void send(final Properties properties) {
        try {
            final JavaMailSenderImpl sender = new JavaMailSenderImpl();
            sender.setHost(properties.getProperty(PROP_HOST));

            final MimeMessage message = getMimeMessage(properties, sender, null, null);

            sender.send(message);
        }
        catch (final MessagingException exception) {
            throw new SystemLoggedException(exception);
        }
    }

    @Override
    public void send(final Properties properties, final String fileName, final String attachmentName) {
        try {
            final JavaMailSenderImpl sender = new JavaMailSenderImpl();
            sender.setHost(properties.getProperty(PROP_HOST));

            final MimeMessage message = getMimeMessage(properties, sender, fileName, attachmentName);
            sender.send(message);
        }
        catch (final MessagingException exception) {
            throw new SystemLoggedException(exception);
        }
    }

    /**
     * Creates and returns the Mime Message needed to send the email.
     *
     * @param properties     the properties.  Must contain the host, the from address, the to address, the subject, and
     *                       the body of the email.
     * @param sender         the mail sender.
     * @param fileName       the file name pointing to the attachement
     * @param attachmentName the name to assign to the attachment.
     * @return the mime message.
     * @throws MessagingException thrown if the attachment cannot be attached.
     */
    private MimeMessage getMimeMessage(final Properties properties, final JavaMailSenderImpl sender,
            final String fileName, final String attachmentName) throws MessagingException {
        final MimeMessage message = sender.createMimeMessage();
        final MimeMessageHelper helper = fileName == null ? new MimeMessageHelper(message)
                : new MimeMessageHelper(message, true);
        helper.setTo(properties.getProperty(PROP_TO));
        helper.setFrom(properties.getProperty(PROP_FROM));
        helper.setSubject(properties.getProperty(PROP_SUBJECT));
        helper.setText(properties.getProperty(PROP_BODY));

        if (fileName == null) {
            return message;
        }

        final File file = new File(fileName);
        helper.addAttachment(attachmentName, file);

        return message;
    }
}
