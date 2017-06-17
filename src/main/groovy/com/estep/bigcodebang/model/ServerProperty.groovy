package com.estep.bigcodebang.model

/**
 * Returns properties related to the Server project.
 */
class ServerProperty {
    static ServerProperty INSTANCE
    ConfigObject configs

    private ServerProperty() {
        configs = new ConfigSlurper().parse(ModelProperty.class)

        final URL url = this.getClass().getClassLoader().getResource("server_project.properties").toURI().toURL()
        final ConfigObject projectProps = new ConfigSlurper().parse(url)
        configs.merge(projectProps)
    }

    private static ServerProperty instanceOf() {
        if (INSTANCE == null) {
            INSTANCE = new ServerProperty()
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
        ServerProperty manager = ServerProperty.instanceOf()
        def value

        def elements = propertyName.split("\\.")
        for (element in elements) {
            value = value == null ? manager.configs[element] : value[element]
        }

        value instanceof ConfigObject ? null : value
    }
}
