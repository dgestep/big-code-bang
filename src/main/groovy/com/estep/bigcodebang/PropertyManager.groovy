package com.estep.bigcodebang

import com.estep.bigcodebang.model.ViewProperty
import com.estep.bigcodebang.model.ServerProperty

/**
 * Manages the validation of the properties provided by the end user.
 *
 * @author dougestep.
 */
class PropertyManager {

    /**
     * Validates the user supplied properties.
     */
    void validate() {
        validateJavaVersion(ServerProperty.get("java_version"))
        validateGradleVersion(ServerProperty.get("gradle_version"))
        validatePort("localhost_port", ViewProperty.get("localhost_port"))
        validatePort("server_port", ViewProperty.get("server_port"))
    }

    /**
     * Validates that the java version is correct.
     * @param value the java version.
     */
    private void validateJavaVersion(String value) {
        BigDecimal version = toNumber("java_version", value)
        if (version.floatValue() < 1.8f) {
            String msg = "The java_version value must be >= 1.8. Received " + value
            throw new IllegalArgumentException(msg)
        }
    }

    /**
     * Validates that the gradle version is correct.
     * @param value the gradle version.
     */
    private void validateGradleVersion(String value) {
        BigDecimal version = toNumber("gradle_version", value)
        if (version.floatValue() < 3.1f) {
            String msg = "The gradle_version value must be >= 3.1. Received " + value
            throw new IllegalArgumentException(msg)
        }
    }

    /**
     * Validates that the supplied port value is correct.
     * @param propertyName the property name that contains the port.
     * @param value the port value.
     */
    private void validatePort(String propertyName, String value) {
        if (value.trim().equals("")) {
            return;
        }
        BigDecimal port = toNumber(propertyName, value)
        if (port.floatValue() < 0) {
            String msg = "Invalid port for property " + propertyName + ". Received " + value
            throw new IllegalArgumentException(msg)
        }
    }

    /**
     * Converts the supplied string value to a number.
     * @param propertyName the property name that contains the string to convert.
     * @param value the string value to convert.
     * @return the converted value.
     */
    private BigDecimal toNumber(String propertyName, String value) {
        try {
            return new BigDecimal(value);
        } catch (NumberFormatException nfe) {
            String msg = "Expecting a numeric value for property " + propertyName + ". Received " + value
            throw new IllegalArgumentException(msg)
        }
    }
}
