package ${topLevelDomain}.${companyName}.${productName}.model.enumeration;

/**
 * Enumeration of all possible roles
 *
 * @author ${codeAuthor}.
 */
public enum Role {
    ADMIN(1, "ADMIN"),
    USER(2, "USER"),
    READONLY(3, "READONLY");

    private int id;
    private String value;

    /**
     * Creates an instance of the enum.
     *
     * @param id    the id key
     * @param value the role name
     */
    Role(final int id, final String value) { // NOCHECKSTYLE
        this.id = id;
        this.value = value;
    }

    /**
     * Returns the ID of the role.
     *
     * @return the ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the value of the role.
     *
     * @return the value.
     */
    public String getValue() {
        return value;
    }

    // CHECKSTYLE:OFF
    /**
     * Returns the {@link Role} enumeration associated with the supplied identifier.
     * @param id identifies the role to return.
     * @return the role or null
     */
    public static Role toEnum(final int id) {
        Role role;
        switch (id) {
            case 1:
                role = Role.ADMIN;
                break;
            case 2:
                role = Role.USER;
                break;
            case 3:
                role = Role.READONLY;
                break;
            default:
                role = null;
                break;
        }
        return role;
    }
    // CHECKSTYLE:ON
}
