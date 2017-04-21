package com.${companyName}.${productName}.restcontroller;

import com.${companyName}.${productName}.model.JsonResponseData;
import com.${companyName}.${productName}.model.data.MessageData;
import com.${companyName}.${productName}.model.enumeration.message.GeneralMessage;
import com.${companyName}.${productName}.model.exception.SystemLoggedException;
import com.${companyName}.${productName}.model.log.LogFactory;
import com.${companyName}.${productName}.model.log.Logger;
import com.${companyName}.${productName}.restcontroller.security.CustomCookieCsrfTokenRepository;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Provides static helper methods used by all services in this project.
 *
 * @author ${codeAuthor}.
 */
public final class ControllerHelper {
    private static final int BUF_CAPACITY = 64;
    /**
     * Default constructor.
     */
    private ControllerHelper() {
    }

    /**
     * MIME type for JSON.
     */
    public static final String APPLICATION_JSON = "application/json; charset=UTF-8";

    /**
     * Writes the supplied message to the HTTP response.  Defaults content type to "text/plain".
     *
     * @param responseCode the HTTP response code.
     * @param message      the message.
     * @param response     the {@link HttpServletResponse}.
     */
    public static void write(final int responseCode, final String message,
            final HttpServletResponse response) {
        write(responseCode, message, response, "text/plain");
    }

    /**
     * Writes the supplied message to the HTTP response.
     *
     * @param responseCode the HTTP response code.
     * @param message      the message.
     * @param response     the {@link HttpServletResponse}.
     * @param contentType  the content type to set to the HTTP response.
     */
    public static void write(final int responseCode, final String message, HttpServletResponse response,
            String contentType) {
        try {
            response.setStatus(responseCode);
            response.setContentType(contentType);
            response.setContentLength(message.length());

            ServletOutputStream out = response.getOutputStream();
            out.println(message);
            out.close();
        }
        catch (final Throwable t) {
            final Logger logger = LogFactory.getLogger();
            logger.error(t.getMessage(), t);
            throw new SystemLoggedException(t);
        }
    }

    /**
     * Copies the contents of the supplied {@link InputStream} to the supplied HTTP response.
     *
     * @param responseCode the HTTP response code.
     * @param in           the {@link InputStream}.
     * @param response     the {@link HttpServletResponse}.
     * @param contentType  the content type to set to the HTTP response.
     */
    public static void writeToOutputStream(final int responseCode, final InputStream in,
            HttpServletResponse response, String contentType) {
        try {
            response.setStatus(responseCode);
            response.setContentType(contentType);
            response.setContentLength(in.available());

            OutputStream out = response.getOutputStream();
            IOUtils.copy(in, out);
        }
        catch (final Throwable t) {
            final Logger logger = LogFactory.getLogger();
            logger.error(t.getMessage(), t);
            throw new SystemLoggedException(t);
        }
    }

    /**
     * Returns the user token contained within the supplied {@link HttpServletRequest}.
     *
     * @param request the HttpServletRequest.
     * @return the token or null if not found.
     */
    public static String getTokenFromRequest(final HttpServletRequest request) {
        final String parameterValue = request
                .getParameter(CustomCookieCsrfTokenRepository.DEFAULT_CSRF_PARAMETER_NAME);
        final String headerValue = request
                .getHeader(CustomCookieCsrfTokenRepository.DEFAULT_CSRF_HEADER_NAME);
        final String userToken;
        if (StringUtils.isNotEmpty(headerValue)) {
            userToken = headerValue;
        } else if (StringUtils.isNotEmpty(parameterValue)) {
            userToken = parameterValue;
        } else {
            userToken = null;
        }
        return userToken;
    }

    /**
     * Returns a {@link ResponseEntity} instance representing successful JSON response.
     *
     * @return the {@link ResponseEntity} instance.
     */
    public static ResponseEntity<JsonResponseData> createSuccessResponse() {
        return createSuccessResponse(null);
    }

    /**
     * Returns a {@link ResponseEntity} instance representing successful JSON response.
     *
     * @param data any data to include. Can be null.
     * @return the {@link ResponseEntity} instance.
     */
    public static ResponseEntity<JsonResponseData> createSuccessResponse(final Object data) {
        JsonResponseData json = new JsonResponseData(HttpServletResponse.SC_OK, GeneralMessage.G007, data);
        return new ResponseEntity<>(json, HttpStatus.valueOf(json.getStatusCode()));
    }

    /**
     * Returns a {@link ResponseEntity} instance containing either a found entity or a not found response.
     *
     * @param result      the found entity or null if not found.
     * @param displayName the entity display to be used in the Not Found message.
     * @param keyFields   the key fields used to retrieve the entity to be used in the Not Found message.
     * @return the {@link ResponseEntity} instance.
     */
    @SuppressWarnings("PMD.UseStringBufferForStringAppends")
    public static ResponseEntity<JsonResponseData> createRetrieveResponse(final Object result,
            final String displayName, final Object... keyFields) {
        final JsonResponseData json;
        if (result == null) {
            final StringBuilder arguments = new StringBuilder(BUF_CAPACITY);
            for (final Object keyField : keyFields) {
                arguments.append(keyField).append(", ");
            }
            String str = arguments.toString();
            final int idx = str.lastIndexOf(", ");
            str = str.substring(0, idx);

            final MessageData message = new MessageData(GeneralMessage.G008, displayName, str);
            json = new JsonResponseData(HttpServletResponse.SC_NOT_FOUND, message);
        } else {
            json = new JsonResponseData(HttpServletResponse.SC_OK, GeneralMessage.G007, result);
        }
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    /**
     * Returns a {@link ResponseEntity} instance containing either a list of entities or a not found response with an
     * empty list of entities.
     *
     * @param results the found entities or empty list if not found.
     * @return the {@link ResponseEntity} instance.
     */
    public static ResponseEntity<JsonResponseData> createListResponse(final List<? extends Object> results) {
        final JsonResponseData json;
        if (results == null || results.isEmpty()) {
            json = new JsonResponseData(HttpServletResponse.SC_NOT_FOUND, GeneralMessage.G009, results);
        } else {
            json = new JsonResponseData(HttpServletResponse.SC_OK, GeneralMessage.G007, results);
        }
        return new ResponseEntity<>(json, HttpStatus.OK);
    }
}
