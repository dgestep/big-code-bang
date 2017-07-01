package com.estep.bigcodebang

/**
 * Created by dougestep on 7/1/17.
 */
class PropertyManagerTests extends GroovyTestCase {
    PropertyManager manager

    void setUp() {
        manager = new PropertyManager()
    }

    void testJavaVersion() {
        String element = manager.validate()
    }
}
