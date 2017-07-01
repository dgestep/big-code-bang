package com.estep.bigcodebang.model

import com.estep.bigcodebang.PropertyManager
import org.codehaus.groovy.control.MultipleCompilationErrorsException

/**
 * Returns properties related to the View project.
 */
class ViewProperty {
    static ViewProperty INSTANCE
    ConfigObject configs

    private ViewProperty() {
    }

    private void initConfiguration() {
        configs = new ConfigSlurper().parse(ModelProperty.class)

        final URL url = this.getClass().getClassLoader().getResource("view_project.properties").toURI().toURL()
        final ConfigObject projectProps = new ConfigSlurper().parse(url)
        configs.merge(projectProps)
    }

    private static ViewProperty instanceOf() {
        if (INSTANCE == null) {
            try {
                INSTANCE = new ViewProperty()
                INSTANCE.initConfiguration()

                PropertyManager manager = new PropertyManager()
                manager.validate()
            } catch (MissingMethodException mme) {
                String msg = "One or more property values contained within the view_project.properties file are " +
                        "not surrounded by double quotes."
                throw new IllegalArgumentException(msg)
            } catch (MultipleCompilationErrorsException mcee) {
                String message = mcee.getMessage()
                if (message.contains("expecting anything but ''\\n''")) {
                    int idx = message.indexOf("@ ")
                    if (idx < 0) {
                        throw mcee
                    } else {
                        String msg = "Property value not surrounded by double quotes within the view_project" +
                                ".properties file " + message.substring(idx)
                        throw new IllegalArgumentException(msg)
                    }
                }
            }
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
