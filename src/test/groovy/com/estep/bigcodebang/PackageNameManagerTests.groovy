package com.estep.bigcodebang

class PackageNameManagerTests extends GroovyTestCase {
    PackageNameManager manager

    void setUp() {
        manager = new PackageNameManager()
    }

    void testKeywords() {
        String element = manager.formatPackageElement("package")
        assert element.equals("_package")

        element = manager.formatPackageElement("PACKAGE")
        assert element.equals("_package")
    }

    void testInvalidChars() {
        String element = manager.formatPackageElement("c*m")
        assert element.equals("c_m")

        element = manager.formatPackageElement("acme-test) company")
        assert element.equals("acme_test__company")
    }

    void testStartsWith() {
        String element = manager.formatPackageElement("-acme")
        assert element.equals("_acme")

        element = manager.formatPackageElement("123 acme")
        assert element.equals("_123_acme")
    }
}
