package com.${companyName}.${productName}.model.util;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

/**
 * * <p>
 * Represents a person's name and provides functionality to break apart the name into it's appropriate
 * attributes (TITLE, FIRST NAME, MIDDLE NAME, LAST NAME, SUFFIX).
 * <p>
 * This class provides two Constructors. The first constructor accepts the full name as a String argument and
 * breaks the full name into it's respective attributes (TITLE, FIRST NAME, MIDDLE NAME, LAST NAME, SUFFIX).
 * After constructing this class, call the appropriate getter method to retrieve each attribute of the full
 * name: getTitle(), getFirstName(), getMiddleName(), getLastName(), getSuffix().
 * <p>
 * Example: construct with "Mr. James Tiberious Kirk I" and this class will parse the string and extract the
 * title, the first name, the middle name, the last name, and the SUFFIX.
 * <p>
 * The second constructor reverses the functionality of the first. It excepts the full name attributes (TITLE,
 * FIRST NAME, MIDDLE NAME, LAST NAME, SUFFIX) as the argument. After constructing this class, call
 * getFullName() method to retrieve the entire name.
 * <p>
 * Example: construct with "Mr.", "James", "Tiberious", "Kirk", "I". Calling getFullName() will return
 * "Mr. James Tiberious Kirk I".
 * <p>
 * This class makes it simple to place one text box on a screen allowing the user to enter a full name and to
 * break apart the entered value into its respective pieces to be stored into database columns etc.
 * </p>
 *
 * @author ${codeAuthor}.
 */
public class NameParser implements Serializable {
    private static final long serialVersionUID = -5038756645781462887L;
    private String nameTitle;
    private String firstName;
    private String middleName;
    private String lastName;
    private String nameSuffix;

    private List<String> tokens;
    private static final String BLANK = " ";
    private static final String[] TITLES = { "DR.", "DR", "MR.", "MR", "MRS.", "MRS", "MS.", "MS", "MISS", "SIR" };
    private static final String[] SUFFIX = { "JR.", "JR", "SR", "SR.", "I", "II", "III", "IV", "V" };
    private static final String[] SUR_NAME_PREFIX = { "VAN", "VON", "DE", "BIN" };

    private static Set<String> nameTitles = new TreeSet<String>();
    private static Set<String> nameSuffixes = new TreeSet<String>();
    private static Set<String> surnamePrefix = new TreeSet<String>();

    static {
        nameTitles.addAll(Arrays.asList(TITLES));
        nameSuffixes.addAll(Arrays.asList(SUFFIX));
        surnamePrefix.addAll(Arrays.asList(SUR_NAME_PREFIX));
    }

    /**
     * Constructs this class and forms the full name from the attributes passed to this constructor.
     *
     * @param nameTitle  the persons title.
     * @param firstName  the persons first name.
     * @param middleName the persons middle name.
     * @param lastName   the persons last name.
     * @param nameSuffix any suffix the person goes by.  Example: Jr. Sr. etc.
     */
    @SuppressWarnings("PMD.ExcessiveParameterList")
    public NameParser(final String nameTitle, final String firstName, final String middleName,
            final String lastName, final String nameSuffix) {
        this.nameTitle = nameTitle;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.nameSuffix = nameSuffix;
    }

    /**
     * Constructs this class and breaks apart the full name into it's respective attributes. Call the
     * appropriate getter methods to get each attribute of the full name.
     *
     * @param fullName the persons full name.  Example: "James Tiberious Kirk".
     */
    public NameParser(final String fullName) {
        tokens = getTokens(fullName);
        final int size = tokens.size();
        if (size == 0) {
            return;
        }

        if (size == 1) {
            setTitle();
            if (tokens.size() == 1) {
                firstName = tokens.get(0);
            }
            return;
        }

        // order here matters...
        setTitle();
        setSuffix();
        setFirstName();

        setLastName();
        setMiddleName();
    }

    /**
     * Returns the individual elements of the supplied full name.
     *
     * @param fullName the persons full name.  Example: "James Tiberious Kirk".
     * @return the elements in a list.  Example: element 0 = "James", element 1 = "Tiberious", element 2 = "Kirk".
     */
    private List<String> getTokens(final String fullName) {
        final List<String> tokens = new ArrayList<>();
        if (StringUtils.isEmpty(fullName)) {
            return tokens;
        }

        final StringTokenizer tokenizer = new StringTokenizer(fullName, BLANK);
        while (tokenizer.hasMoreTokens()) {
            tokens.add(tokenizer.nextToken().trim());
        }

        return tokens;
    }

    /**
     * Extracts the persons title from the tokens and sets the title as a property of this class if the supplied title
     * is a known title to this class.
     */
    private void setTitle() {
        final String title = StringUtils.trim((tokens.get(0)));

        if (nameTitles.contains(title.toUpperCase(Locale.US))) {
            nameTitle = title;
            tokens.remove(0);
        }
    }

    /**
     * Extracts the persons suffix from the tokens and sets the suffix as a property of this class if the supplied
     * suffix is a known suffix to this class.
     */
    private void setSuffix() {
        final int index = tokens.size() - 1;
        final String suffix = StringUtils.trim(tokens.get(index));

        if (nameSuffixes.contains(suffix.toUpperCase(Locale.US))) {
            nameSuffix = suffix;
            tokens.remove(index);
        }
    }

    /**
     * Extracts the persons first name from the tokens and sets the first name as a property of this class.
     */
    private void setFirstName() {
        firstName = StringUtils.trim(tokens.get(0));
        tokens.remove(0);
    }

    /**
     * Extracts the persons last name from the tokens and sets the last name as a property of this class.
     */
    private void setLastName() {
        final int index = getTokenIndexForSurname();
        if (index < 0) {
            // grab the last token value as the last name
            final int z = tokens.size() - 1;
            lastName = StringUtils.trim(tokens.get(z));
            tokens.remove(z);
            return;
        }

        // include the surname as part of the last name.
        final StringBuilder buf = new StringBuilder();
        for (int x = index, y = tokens.size(); x < y; x++) {
            final String name = StringUtils.trim(tokens.get(index));
            buf.append(name).append(BLANK);
            tokens.remove(index);
        }
        lastName = buf.toString().trim();
    }

    /**
     * Returns index within the tokens array where the surname is located.
     *
     * @return the index or -1 if not found.
     */
    private int getTokenIndexForSurname() {
        int index = -1;
        for (final String prefix : surnamePrefix) {
            index = indexOf(prefix);
            if (index > -1) {
                break;
            }
        }
        return index;
    }

    /**
     * Returns the index within the tokens array where the supplied value is located.
     *
     * @param val the value to look for.
     * @return the index or -1 if not found.
     */
    private int indexOf(String val) {
        val = val.toUpperCase(Locale.US);
        boolean foundIndex = false;
        int index = 0;
        for (final int n = tokens.size(); index < n; index++) {
            final String token = tokens.get(index);
            if (token.equalsIgnoreCase(val)) {
                foundIndex = true;
                break;
            }
        }
        return foundIndex ? index : -1;
    }

    /**
     * Extracts the persons middle name from the tokens and sets the middle name as a property of this class.
     */
    private void setMiddleName() {
        if (tokens.size() == 0) {
            return;
        }

        final StringBuilder buf = new StringBuilder();
        for (int x = 0, y = tokens.size(); x < y; x++) {
            final String name = StringUtils.trim(tokens.get(x));
            buf.append(name).append(BLANK);
        }

        middleName = buf.toString().trim();
    }

    /**
     * Returns the full name. If any attribute is null, it is converted to empty string.
     *
     * @return the full name or null.
     */
    public String getFullName() {
        final StringBuilder buf = new StringBuilder(64);
        if (StringUtils.isNotEmpty(nameTitle)) {
            buf.append(StringUtils.defaultString(nameTitle)).append(BLANK);
        }
        buf.append(StringUtils.defaultString(firstName)).append(BLANK);
        if (StringUtils.isNotEmpty(middleName)) {
            buf.append(StringUtils.defaultString(middleName)).append(BLANK);
        }
        if (StringUtils.isNotEmpty(lastName)) {
            buf.append(StringUtils.defaultString(lastName)).append(BLANK);
        }
        if (StringUtils.isNotEmpty(nameSuffix)) {
            buf.append(StringUtils.defaultString(nameSuffix));
        }
        return buf.toString().trim();
    }

    /**
     * Returns the title.
     *
     * @return the title.
     */
    public String getTitle() {
        return StringUtils.defaultString(nameTitle).trim();
    }

    /**
     * Returns the first name.
     *
     * @return the first name.
     */
    public String getFirstName() {
        return StringUtils.defaultString(firstName).trim();
    }

    /**
     * Returns the middle name.
     *
     * @return the middle name.
     */
    public String getMiddleName() {
        return StringUtils.defaultString(middleName).trim();
    }

    /**
     * Returns the last name.
     *
     * @return the last name.
     */
    public String getLastName() {
        return StringUtils.defaultString(lastName).trim();
    }

    /**
     * Returns the SUFFIX.
     *
     * @return the suffix.
     */
    public String getSuffix() {
        return StringUtils.defaultString(nameSuffix).trim();
    }

    @Override
    public String toString() {
        return getFullName();
    }

    /**
     * Returns a Set containing all the possible TITLES for a name.
     *
     * @return the known titles.
     */
    public static Set<String> getTitles() {
        return nameTitles;
    }

    /**
     * Returns a Set containing all the possible suffixes for a name.
     *
     * @return the known suffixes.
     */
    public static Set<String> getSuffixes() {
        return nameSuffixes;
    }

    /**
     * Returns a Set containing all the possible Surnames for a name.
     *
     * @return the known surnames.
     */
    public static Set<String> getSurnamePrefixes() {
        return surnamePrefix;
    }
}
