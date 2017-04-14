package com.estep.webkickstart.model

class PropertyTests extends GroovyTestCase {
    Property manager

    void setUp() {
        manager = new Property();
    }

    void tearDown() {
    }

    void testGetPropertyExpectFound() {
        def companyName = Property.get("company_name")
        assert companyName.equals("esteptek")
    }

    void testGetPropertyExpectNotFound() {
        def companyName = Property.get("notfound")
        assert companyName == null
    }
}
