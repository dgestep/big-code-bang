package com.estep.bigcodebang.model

class ServerPropertyTests extends GroovyTestCase {
    void testGetPropertyExpectFound() {
        def companyName = ServerProperty.get("company_name")
        assert companyName.equals("esteptek")
    }

    void testGetPropertyExpectNotFound() {
        def companyName = ServerProperty.get("notfound")
        assert companyName == null
    }

    void testGetPropertyWithSlashInValue() {
        def location = ServerProperty.get("application_log_file_location")
        assert location.equals("C:\\\\temp\\\\logs")
    }
}
