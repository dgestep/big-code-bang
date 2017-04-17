package com.${companyName}.${productName}.model.service.security;

import com.${companyName}.${productName}.model.data.UserData;
import com.${companyName}.${productName}.model.data.UserToken; // NOCHECKSTYLE

/**
 * Defines a class which authenticates a user in the application.
 *
 * @author ${codeAuthor}.
 */
public interface AuthenticationService {

    /**
     * Authenticates the supplied user and returns a security token if successful.
     *
     * @param emailAddress the email address.
     * @param password     the password.
     * @param autoLogin    supply true to automatically login without authentication.
     * @return the authenticated user information.
     */
    UserData authenticate(String emailAddress, String password, boolean autoLogin);

    /**
     * Validates the supplied token and returns a {@link UserToken} instance containing the embedded information within
     * the token.
     *
     * @param token the token to apply
     * @return the {@link UserToken} instance or null if the token is invalid.
     */
    UserData applyToken(String token);

    /**
     * Abandons a users access to the protected resources.
     *
     * @param token the token used to identify the user.
     */
    void invalidate(String token);
}
