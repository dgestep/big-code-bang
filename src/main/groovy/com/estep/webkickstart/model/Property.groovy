package com.estep.webkickstart.model

/**
 * Manages the interaction between classes and project properties.
 */
class Property {
    static Property INSTANCE
    ConfigObject configs

    private Property() {
        configs = new ConfigSlurper().parse(ModelProperty.class)

        final URL url = this.getClass().getClassLoader().getResource("project.properties").toURI().toURL()
        final ConfigObject projectProps = new ConfigSlurper().parse(url)
        configs.merge(projectProps)
    }

    private static Property instanceOf() {
        if (INSTANCE == null) {
            INSTANCE = new Property()
        }
        return INSTANCE
    }

    /**
     * Returns the value of the supplied property.
     *
     * @param propertyName the property.
     * @return the value or null if not found.
     */
    static String get(propertyName) {
        Property manager = Property.instanceOf()
        def value

        def elements = propertyName.split("\\.")
        for (element in elements) {
            value = value == null ? manager.configs[element] : value[element]
        }

        value instanceof ConfigObject ? null : value
    }
}
