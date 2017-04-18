package com.${companyName}.${productName}.model.repository.user;

/**
 * Validates a users password.
 *
 * @author ${codeAuthor}.
 */
public interface PasswordValidator {

    /**
     * Encrypts the supplied password.
     *
     * @param plainTextPassword the password in plain unencrypted text to encrypt.
     * @return the encrypted password.
     */
    String encryptPassword(String plainTextPassword);

    /**
     * Returns true if the supplied plain text password matches the supplied encryptedPassword.
     *
     * @param plainTextPassword the password in plain unencrypted text.
     * @param encryptedPassword the encrypted password to compare with.
     * @return true if the two passwords are identical.
     */
    boolean isValidPassword(String plainTextPassword, String encryptedPassword);

    /**
     * Returns true if the supplied plain text password DOES NOT match the supplied encryptedPassword.
     *
     * @param plainTextPassword the password in plain unencrypted text.
     * @param encryptedPassword the encrypted password to compare with.
     * @return true if the two passwords are NOT identical.
     */
    boolean isNotValidPassword(String plainTextPassword, String encryptedPassword);
}
