package ${topLevelDomain}.${companyName}.${productName}.model.log;

import ${topLevelDomain}.${companyName}.${productName}.model.EnvironmentConfiguration;
import ${topLevelDomain}.${companyName}.${productName}.model.enumeration.Region;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.xml.DOMConfigurator;

import java.net.URL;

/**
 * Helper class used to return an instance of {@link Logger}.
 *
 * @author ${codeAuthor}.
 */
public final class LogFactory {
    private static boolean initialized;

    /**
     * Default constructor.
     */
    private LogFactory() {
        initLogger();
    }

    /**
     * Initialize the log file.
     */
    private static void initLogger() {
        if (initialized) {
            return;
        }

        initialized = true;

        System.out.println("");
        System.out.println("=> Begin System Logging Configuration");
        URL fileUrl = null;
        String filePath = null;

        try {
            final Region region = EnvironmentConfiguration.getRegion();
            filePath = "log4j-" + region.getCode() + ".xml";
            System.out.println("=> searching for log configuration file : " + filePath);
            fileUrl = LogFactory.class.getResource("/" + filePath);
            DOMConfigurator.configure(fileUrl);
            System.out.println("=> found log configuration file : " + fileUrl);
            System.out.println("=> System Logging has been initialized");
        }
        catch (final NoClassDefFoundError ncdf) {
            System.out.println("=> Logging Configuration Failed");
            System.err.println(LogFactory.class.getName()
                    + "::loadProps() - Unable to find needed classes. Logging disabled. " + ncdf);
        }
        catch (final Exception e) {
            System.out.println("=> Logging Configuration Failed");
            System.err.println(
                    LogFactory.class.getName() + "::loadProps() - Unable to initialize logger from file="
                            + filePath + ". Logging disabled.");
        }
        System.out.println("=> End System Logging Configuration");
        System.out.println("");
    }

    /**
     * Creates a {@link Logger} instance for the class of the calling client.
     *
     * @return the {@link Logger}
     */
    public static Logger getLogger() {
        initLogger();
        return new Log4JLoggerImpl(1);
    }

    /**
     * Allows the caller to get an arbitrary logger for the parameter passed in.
     *
     * @param namedLogger The name for the logger.
     * @return A logger for the named value.
     * @throws IllegalArgumentException if the namedLogger is null or empty.
     */
    public static Logger getNamedLogger(final String namedLogger) throws IllegalArgumentException {
        initLogger();
        if (StringUtils.isEmpty(namedLogger)) {
            throw new IllegalArgumentException("namedLogger can not be null or empty");
        }
        return new Log4JLoggerImpl(namedLogger);
    }
}
