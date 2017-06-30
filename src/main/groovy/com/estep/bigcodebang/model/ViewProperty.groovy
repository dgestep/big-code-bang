package com.estep.bigcodebang.model

/**
 * Returns properties related to the View project.
 */
class ViewProperty {
    static ViewProperty INSTANCE
    ConfigObject configs

    private ViewProperty() {
        configs = new ConfigSlurper().parse(WebProperty.class)

        final URL url = this.getClass().getClassLoader().getResource("view_project.properties").toURI().toURL()
        final ConfigObject projectProps = new ConfigSlurper().parse(url)
        configs.merge(projectProps)
    }

    private static ViewProperty instanceOf() {
        if (INSTANCE == null) {
            INSTANCE = new ViewProperty()
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
        ViewProperty manager = ViewProperty.instanceOf()
        def value

        def elements = propertyName.split("\\.")
        for (element in elements) {
            value = value == null ? manager.configs[element] : value[element]
        }

        value = value instanceof ConfigObject ? null : value
        if (value == null) {
            return null;
        }

        String propertyValue = (String) value;
        if (propertyValue != null) {
            propertyValue = propertyValue.replace("\\", "\\\\")
        }
        propertyValue
    }
}
