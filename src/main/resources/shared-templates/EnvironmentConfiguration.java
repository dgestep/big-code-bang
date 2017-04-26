package ${topLevelDomain}.${companyName}.${productName}.model;

import ${topLevelDomain}.${companyName}.${productName}.model.enumeration.Region;

/**
 * Evaluates the System property information to determine the Region.
 *
 * @author ${codeAuthor}.
 */
public final class EnvironmentConfiguration {
    private static final String RUNTIME_SYSTEM_PROPERTY = "runtime.environment";
    private static Region region;

    /**
     * Default constructor.
     */
    private EnvironmentConfiguration() {
    }

    /**
     * Returns the {@link Region} where this application is running.
     *
     * @return the region.
     */
    public static Region getRegion() {
        final Region region;
        if (EnvironmentConfiguration.region == null) {
            region = initRegion();
        } else {
            region = EnvironmentConfiguration.region;
        }

        return region;
    }

    /**
     * Initializes the region.
     *
     * @return the region.
     */
    private static synchronized Region initRegion() {
        System.out.println("");
        System.out.println("=> Begin Runtime Environment Region Configuration");

        System.out.println("=> Looking for System Property named: " + RUNTIME_SYSTEM_PROPERTY);
        final String regionValue = System.getProperty(RUNTIME_SYSTEM_PROPERTY);
        System.out.println("=> " + RUNTIME_SYSTEM_PROPERTY + ": " + regionValue);

        region = Region.toRegionByCode(regionValue);
        System.out.println("=> Runtime Environment: " + region.getDisplayName());

        System.out.println("=> End Runtime Environment Region Configuration");
        System.out.println("");

        return region;
    }
}
