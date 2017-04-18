package com.${companyName}.${productName}.model.repository.user;

import org.springframework.stereotype.Repository;

import java.util.Random;

/**
 * Generates a new password.
 *
 * @author ${codeAuthor}.
 */
@Repository("PasswordGeneratorRepository")
public class PasswordGeneratorRepositoryImpl implements PasswordGeneratorRepository {
    private static final Random RANDOM_GENERATOR = new Random();
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBERS = "0123456789";
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyz!_-@&^*";
    private static final String TEMPLATE = UPPER + NUMBERS + CHARACTERS;
    private static final int MAX_SIZE = 16;

    @Override
    public String generate() {
        final StringBuilder buf = new StringBuilder();

        for (int idx = 0; idx < MAX_SIZE; idx++) {
            buf.append(randomCharacter(TEMPLATE));
        }
        ensureOneFound(buf, UPPER, true);
        ensureOneFound(buf, NUMBERS, false);
        return buf.toString();
    }

    /**
     * Ensures at least one character from the supplied template exists in the supplied buffer.
     *
     * @param buf       the buffer.
     * @param template  the template.
     * @param beginning supply true to prepend a character to the beginning of the password if need be.
     */
    private void ensureOneFound(final StringBuilder buf, final String template, final boolean beginning) {
        boolean found = false;
        final String password = buf.toString();
        for (int i = 0, n = password.length(); i < n; i++) {
            final String ch = password.substring(i, i + 1);
            if (template.contains(ch)) {
                found = true;
                break;
            }
        }

        if (!found) {
            removeEnd(buf);
            if (beginning) {
                buf.insert(0, randomCharacter(template));
            } else {
                buf.append(randomCharacter(template));
            }
        }
    }

    /**
     * Removes the last character off of the end of the supplied {@link StringBuilder}.
     *
     * @param buf the StringBuilder.
     */
    private void removeEnd(final StringBuilder buf) {
        final int size = buf.length();
        final int endIdx = size - 1;
        buf.delete(endIdx, size);
    }

    /**
     * Generates a character randomly picked from the supplied template.
     *
     * @param template the string template.
     * @return the character.
     */
    private char randomCharacter(final String template) {
        final int bound = template.length();
        final int idx = generateRandomNumber(bound);
        return template.charAt(idx);
    }

    /**
     * Generates a random integer between 0 (inclusive) and the supplied upper boundary (exclusive).
     *
     * @param bound the upper boundary.
     * @return the random integer.
     */
    private int generateRandomNumber(final int bound) {
        return RANDOM_GENERATOR.nextInt(bound);
    }
}
