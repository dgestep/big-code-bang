package ${topLevelDomain}.${companyName}.${productName}.restcontroller;


import ${topLevelDomain}.${companyName}.${productName}.model.EnvironmentConfiguration;
import ${topLevelDomain}.${companyName}.${productName}.model.enumeration.Region;
import ${topLevelDomain}.${companyName}.${productName}.model.log.LogFactory;
import ${topLevelDomain}.${companyName}.${productName}.model.log.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Initializes resources used throughout the web application.
 *
 * @author ${codeAuthor}.
 */
public class EnvironmentSetupListener implements ServletContextListener {

    @Override
    public void contextInitialized(final ServletContextEvent ctx) {
        final Region r = EnvironmentConfiguration.getRegion();
        final Logger logger = LogFactory.getLogger();
        logger.info("Logging has been initialized!");
        logger.info("Region is set to ==>" + r.name() + "<==");
    }

    @Override
    public void contextDestroyed(final ServletContextEvent ctx) {
    }
}
