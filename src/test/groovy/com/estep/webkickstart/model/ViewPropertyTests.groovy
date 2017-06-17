package com.estep.bigcodebang.model

class ViewPropertyTests extends GroovyTestCase {

    void testGetPropertyExpectFound() {
        def title = ViewProperty.get("application_title")
        assert title.equals("Test Application")
    }

    void testGetPropertyExpectNotFound() {
        def bogus = ViewProperty.get("notfound")
        assert bogus == null
    }
}
