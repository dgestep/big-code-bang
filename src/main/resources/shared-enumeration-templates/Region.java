package ${topLevelDomain}.${companyName}.${productName}.model.enumeration;

/**
 * Represents the environment in which this application is running.
 *
 * @author ${codeAuthor}.
 */
public enum Region {
    /**
     * Defines our Development region.
     */
    DEVELOPMENT("development", "Development"),
    /**
     * Defines our Certification region.
     */
    CERTIFICATION("cert", "Certification"),
    /**
     * Defines our Production region.
     */
    PRODUCTION("production", "Production");

    private String code;
    private String displayName;

    /**
     * Creates an instance of this enumeration.
     *
     * @param code        the internal code.
     * @param displayName the name suitable for display.
     */
    Region(final String code, final String displayName) { // NOCHECKSTYLE
        this.code = code;
        this.displayName = displayName;
    }

    /**
     * Returns the equivalent Region enumeration for the supplied region code.
     *
     * @param code the code. Can be null.
     * @return the Region enumeration or null if none can be found.
     */
    public static Region toRegionByCode(final String code) {
        // defaults to production
        Region region = Region.PRODUCTION;
        if (code == null) {
            return region;
        }

        if (code.equalsIgnoreCase("development")) {
            region = Region.DEVELOPMENT;
        } else if (code.equalsIgnoreCase("cert")) {
            region = Region.CERTIFICATION;
        } else if (code.equalsIgnoreCase("production")) {
            region = Region.PRODUCTION;
        }

        return region;
    }

    /**
     * Returns the region code for this enumeration.
     *
     * @return the code.
     */
    public String getCode() {
        return code;
    }

    /**
     * Returns the region name suitable for displaying on a screen.
     *
     * @return the display name.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Assigns this regions code to a System property identified by the supplied property name.
     *
     * @param propertyName the property name.
     */
    public void setRegionToSystemProperty(final String propertyName) {
        System.setProperty(propertyName, getCode());
    }
}
