package ${topLevelDomain}.${companyName}.${productName}.restcontroller.security;

import ${topLevelDomain}.${companyName}.${productName}.model.data.UserData;

/**
 * {@link ThreadLocal} implementation containing the authenticated user.
 *
 * @author ${codeAuthor}.
 */
public final class UserThreadLocal extends ThreadLocal<UserData> {
    private static ThreadLocal<UserData> threadLocal = new ThreadLocal<>();

    /**
     * Default constructor.
     */
    private UserThreadLocal() {
    }

    /**
     * Returns the user assigned to this {@link ThreadLocal} implementation.
     *
     * @return the user or null if nothing is assigned.
     */
    public static UserData getUser() {
        return threadLocal.get();
    }

    /**
     * Sets the user to this {@link ThreadLocal} implementation
     *
     * @param user the user.
     */
    public static void setUser(final UserData user) {
        if (user != null) {
            threadLocal.set(user);
        }
    }
}
