package com.estep.bigcodebang

import com.estep.bigcodebang.model.ViewProperty
import com.estep.bigcodebang.model.ServerProperty

/**
 * Manages the validation of the properties provided by the end user.
 *
 * @author dougestep.
 */
class PropertyManager {

    void validate() {
        validateJavaVersion(ServerProperty.get("java_version"))
        validateGradleVersion(ServerProperty.get("gradle_version"))
        validatePort("localhost_port", ViewProperty.get("localhost_port"))
        validatePort("server_port", ViewProperty.get("server_port"))
    }

    private void validateJavaVersion(String value) {
        BigDecimal version = toNumber("java_version", value)
        if (version.floatValue() < 1.8f) {
            String msg = "The java_version value must be >= 1.8. Received " + value
            throw new IllegalArgumentException(msg)
        }
    }

    private void validateGradleVersion(String value) {
        BigDecimal version = toNumber("gradle_version", value)
        if (version.floatValue() < 3.1f) {
            String msg = "The gradle_version value must be >= 3.1. Received " + value
            throw new IllegalArgumentException(msg)
        }
    }

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

    private BigDecimal toNumber(String propertyName, String value) {
        try {
            return new BigDecimal(value);
        } catch (NumberFormatException nfe) {
            String msg = "Expecting a numeric value for property " + propertyName + ". Received " + value
            throw new IllegalArgumentException(msg)
        }
    }
}
