package com.${companyName}.${productName}.restcontroller;

import com.${companyName}.${productName}.model.JsonResponseData;
import com.${companyName}.${productName}.model.exception.SystemLoggedException;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

/**
 * Provides a service to verify the services layer is up and running.
 *
 * @author ${codeAuthor}.
 */
@RestController
@RequestMapping("/health")
public class HealthCheckController {
    private static final String VERSION_FILE = "/version.txt";
    private String version;
    @Autowired
    private ServletContext servletContext;

    /**
     * Returns a JSON response containing the health of the services.
     *
     * @return the JSON response.
     */
    @RequestMapping(value = "/version", method = RequestMethod.GET, produces = { ControllerHelper.APPLICATION_JSON })
    public ResponseEntity<JsonResponseData> version() {
        InputStream in = null;
        InputStreamReader reader = null;
        BufferedReader bufferedReader = null;
        try {
            if (StringUtils.isEmpty(version)) {
                in = servletContext.getResourceAsStream(VERSION_FILE);
                reader = new InputStreamReader(in, StandardCharsets.UTF_8);
                bufferedReader = new BufferedReader(reader);
                version = bufferedReader.readLine();
            }
        }
        catch (final IOException io) {
            throw new SystemLoggedException(io);
        }
        finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(reader);
            IOUtils.closeQuietly(bufferedReader);
        }
        return ControllerHelper.createSuccessResponse(new VersionData(version));
    }

    /**
     * Returns a JSON response containing the health of the services.
     *
     * @return the JSON response.
     */
    @RequestMapping(method = RequestMethod.GET, produces = { ControllerHelper.APPLICATION_JSON })
    public ResponseEntity<JsonResponseData> health() {
        final HealthData health = new HealthData(getNodeName());
        return ControllerHelper.createSuccessResponse(health);
    }

    /**
     * Returns the host node name that this restcontroller is running on.
     *
     * @return the host node name.
     */
    private String getNodeName() {
        String name;
        try {
            final InetAddress address = InetAddress.getLocalHost();
            name = address.toString();
        }
        catch (final UnknownHostException uhe) {
            throw new SystemLoggedException(uhe);
        }
        return name;
    }

    /**
     * Container for the health information
     *
     * @author dougestep.
     */
    @SuppressFBWarnings
    @SuppressWarnings("PMD")
    // CHECKSTYLE:OFF
    private class HealthData {
        private String nodeName;

        public HealthData(String nodeName) {
            setNodeName(nodeName);
        }

        public void setNodeName(String nodeName) {
            this.nodeName = nodeName;
        }
    }
    // CHECKSTYLE:ON

    /**
     * Container for the version information
     *
     * @author dougestep.
     */
    @SuppressFBWarnings
    @SuppressWarnings("PMD")
    // CHECKSTYLE:OFF
    private class VersionData {
        private String version;

        public VersionData(String version) {
            setVersion(version);
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getVersion() {
            return version;
        }
    }
    // CHECKSTYLE:ON
}
