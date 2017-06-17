package com.estep.bigbangcode.model

class ServerPropertyTests extends GroovyTestCase {
    void testGetPropertyExpectFound() {
        def companyName = ServerProperty.get("company_name")
        assert companyName.equals("esteptek")
    }

    void testGetPropertyExpectNotFound() {
        def companyName = ServerProperty.get("notfound")
        assert companyName == null
    }
}
