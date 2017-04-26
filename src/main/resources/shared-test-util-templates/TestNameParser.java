package ${topLevelDomain}.${companyName}.${productName}.model.util;

import mockit.integration.junit4.JMockit;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

@RunWith(JMockit.class)
public class TestNameParser {
    private static final String[] TITLES = { "DR.", "DR", "MR.", "MR", "MRS.", "MRS", "MS.", "MS", "MISS", "SIR" };
    private static final String[] SUFFIX = { "JR.", "JR", "SR", "SR.", "I", "II", "III", "IV", "V" };
    private static final String[] SUR_NAME_PREFIX = { "VAN", "VON", "DE", "BIN" };

    private NameParser sut;

    private void assertNameParts(String title, String firstName, String middleName, String lastName, String suffix) {
        Assert.assertEquals(title, sut.getTitle());
        Assert.assertEquals(firstName, sut.getFirstName());
        Assert.assertEquals(middleName, sut.getMiddleName());
        Assert.assertEquals(lastName, sut.getLastName());
        Assert.assertEquals(suffix, sut.getSuffix());
    }

    @Test
    public void testConstructWithElements() {
        sut = new NameParser("Mr.", "James", "Tiberious", "Kirk", "Jr.");
        assertNameParts("Mr.", "James", "Tiberious", "Kirk", "Jr.");
        Assert.assertEquals(sut.getFullName(), "Mr. James Tiberious Kirk Jr.");
    }

    @Test
    public void testConstructWithFullName() {
        sut = new NameParser("Mr. James Tiberious Kirk Jr.");
        assertNameParts("Mr.", "James", "Tiberious", "Kirk", "Jr.");
        Assert.assertEquals("Mr.", sut.getTitle());
        Assert.assertEquals("James", sut.getFirstName());
        Assert.assertEquals("Tiberious", sut.getMiddleName());
        Assert.assertEquals("Kirk", sut.getLastName());
        Assert.assertEquals("Jr.", sut.getSuffix());
    }

    @Test
    public void testConstructWithFullNameJustFirstNameSupplied() {
        sut = new NameParser("James");
        assertNameParts("", "James", "", "", "");
        Assert.assertEquals(sut.getFullName(), "James");
    }

    @Test
    public void testConstructWithNull() {
        sut = new NameParser(null);
        assertNameParts("", "", "", "", "");
        Assert.assertEquals(sut.getFullName(), "");
    }

    @Test
    public void testConstructWithJustTitle() {
        sut = new NameParser("Mrs.");
        assertNameParts("Mrs.", "", "", "", "");
        Assert.assertEquals(sut.getFullName(), "Mrs.");
    }

    @Test
    public void testConstructWithFullNameNoSuffix() {
        sut = new NameParser("Mr. James Tiberious Kirk");
        assertNameParts("Mr.", "James", "Tiberious", "Kirk", "");
        Assert.assertEquals(sut.getFullName(), "Mr. James Tiberious Kirk");
    }

    @Test
    public void testConstructWithFullNameNoTitleNoSuffix() {
        sut = new NameParser("James Tiberious Kirk");
        assertNameParts("", "James", "Tiberious", "Kirk", "");
        Assert.assertEquals(sut.getFullName(), "James Tiberious Kirk");
    }

    @Test
    public void testConstructWithFullNameNoTitleNoMiddleNameNoSuffix() {
        sut = new NameParser("James Kirk");
        assertNameParts("", "James", "", "Kirk", "");
        Assert.assertEquals(sut.getFullName(), "James Kirk");
    }

    @Test
    public void testConstructWithFullNameWithExtraSpaces() {
        sut = new NameParser("   James  Tiberious  Kirk   ");
        assertNameParts("", "James", "Tiberious", "Kirk", "");
        Assert.assertEquals(sut.toString(), "James Tiberious Kirk");
    }

    @Test
    public void testConstructWithFullNameNoTitleNoSuffixWithSurName() {
        sut = new NameParser("John Eddie Van Halen");
        assertNameParts("", "John", "Eddie", "Van Halen", "");
        Assert.assertEquals(sut.getFullName(), "John Eddie Van Halen");
    }

    @Test
    public void testConstructWithLongName() {
        sut = new NameParser("Billy Jo Jim Bob Smith the Great!");
        assertNameParts("", "Billy", "Jo Jim Bob Smith the", "Great!", "");
        Assert.assertEquals(sut.getFullName(), "Billy Jo Jim Bob Smith the Great!");
    }

    @Test
    public void testStaticTitles() {
        Set<String> nameTitles = new TreeSet<String>();
        nameTitles.addAll(Arrays.asList(TITLES));
        Assert.assertEquals(nameTitles, NameParser.getTitles());
    }

    @Test
    public void testStaticSuffix() {
        Set<String> nameSuffixes = new TreeSet<String>();
        nameSuffixes.addAll(Arrays.asList(SUFFIX));
        Assert.assertEquals(nameSuffixes, NameParser.getSuffixes());
    }

    @Test
    public void testStaticSurNamePrefix() {
        Set<String> surnamePrefix = new TreeSet<String>();
        surnamePrefix.addAll(Arrays.asList(SUR_NAME_PREFIX));
        Assert.assertEquals(surnamePrefix, NameParser.getSurnamePrefixes());
    }
}
