package com.${companyName}.${productName}.restcontroller.security;

import com.${companyName}.${productName}.model.JsonResponseData;
import com.${companyName}.${productName}.model.data.UserData;
import com.${companyName}.${productName}.model.service.security.AuthenticationService;
import com.${companyName}.${productName}.restcontroller.ControllerHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * The REST entry point for the security services.
 *
 * @author ${codeAuthor}.
 */
@RestController
@RequestMapping("/security")
public class SecurityController {
    @Resource(name = "AuthenticationService")
    private AuthenticationService authenticationService;

    @Resource(name = "tokenRepository")
    private CsrfTokenRepository tokenRepository;

    /**
     * The entry point to login a user.
     *
     * @param emailAddress the email address.
     * @param password     the password.
     * @param request      the HTTP request.
     * @param response     the HTTP response.
     * @return the JSON response.
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = { ControllerHelper.APPLICATION_JSON })
    public ResponseEntity<JsonResponseData> login(@RequestParam String emailAddress, @RequestParam String password,
            final HttpServletRequest request, final HttpServletResponse response) {
        final UserData userData = authenticationService.authenticate(emailAddress, password, false);

        final String token = userData.getToken();
        // saves the CSRF token in a cookie
        final CsrfToken csrfToken = new DefaultCsrfToken(
                CustomCookieCsrfTokenRepository.DEFAULT_CSRF_HEADER_NAME,
                CustomCookieCsrfTokenRepository.DEFAULT_CSRF_PARAMETER_NAME, token);
        tokenRepository.saveToken(csrfToken, request, response);

        return ControllerHelper.createSuccessResponse(userData);
    }

    /**
     * The entry point to auto login a user.
     *
     * @param emailAddress the email address.
     * @param request      the HTTP request.
     * @param response     the HTTP response.
     * @return the JSON response.
     */
    @RequestMapping(value = "/auto-login", method = RequestMethod.POST, produces = {
            ControllerHelper.APPLICATION_JSON })
    public ResponseEntity<JsonResponseData> autoLogin(@RequestParam String emailAddress,
            final HttpServletRequest request, final HttpServletResponse response) {
        final UserData userData = authenticationService.authenticate(emailAddress, "", true);

        final String token = userData.getToken();
        // saves the CSRF token in a cookie
        final CsrfToken csrfToken = new DefaultCsrfToken(
                CustomCookieCsrfTokenRepository.DEFAULT_CSRF_HEADER_NAME,
                CustomCookieCsrfTokenRepository.DEFAULT_CSRF_PARAMETER_NAME, token);
        tokenRepository.saveToken(csrfToken, request, response);

        return ControllerHelper.createSuccessResponse(userData);
    }

    /**
     * Logs off a user.
     *
     * @param request  the HTTP request.
     * @param response the HTTP response.
     * @return the JSON response.
     */
    @Secured(value = "USER")
    @RequestMapping(value = "/logoff", method = RequestMethod.POST, produces = { ControllerHelper.APPLICATION_JSON })
    public ResponseEntity<JsonResponseData> logoff(final HttpServletRequest request,
            final HttpServletResponse response) {
        // invalidate the token
        final String userToken = ControllerHelper.getTokenFromRequest(request);
        authenticationService.invalidate(userToken);

        // removes the CSRF token by removing the cookie
        tokenRepository.saveToken(null, request, response);

        // invalidate the session
        final HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return ControllerHelper.createSuccessResponse();
    }
}
