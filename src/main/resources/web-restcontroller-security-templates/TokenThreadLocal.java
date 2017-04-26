package ${topLevelDomain}.${companyName}.${productName}.restcontroller.security;

/**
 * {@link ThreadLocal} implementation containing the authentication token.
 *
 * @author ${codeAuthor}.
 */
public final class TokenThreadLocal extends ThreadLocal<String> {
    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    /**
     * Default constructor.
     */
    private TokenThreadLocal() {
    }

    /**
     * Returns the token assigned to this {@link ThreadLocal} implementation.
     *
     * @return the token or null if nothing is assigned.
     */
    public static String getToken() {
        return threadLocal.get();
    }

    /**
     * Sets the token to this {@link ThreadLocal} implementation
     *
     * @param token the token.
     */
    public static void setToken(final String token) {
        if (token != null) {
            threadLocal.set(token);
        }
    }
}
