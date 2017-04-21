package com.${companyName}.${productName}.restcontroller.security;

import com.${companyName}.${productName}.model.JsonResponseData;
import com.${companyName}.${productName}.model.data.UserData;
import com.${companyName}.${productName}.model.enumeration.message.SecurityMessage;
import com.${companyName}.${productName}.model.service.security.AuthenticationService;
import com.${companyName}.${productName}.restcontroller.ControllerHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Authentication filter for REST services.
 *
 * @author ${codeAuthor}.
 */
public class RestServiceFilter extends UsernamePasswordAuthenticationFilter {
    @Resource(name = "AuthenticationService")
    private AuthenticationService authenticationService;

    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request,
            final HttpServletResponse response) throws AuthenticationException {
        final JsonResponseData jsonData =
                new JsonResponseData(HttpServletResponse.SC_UNAUTHORIZED, SecurityMessage.SC003);
        final String json = JsonResponseData.toJson(jsonData);
        ControllerHelper.write(HttpServletResponse.SC_OK, json, response, "application/json; charset=UTF-8");

        return null;
    }

    @Override
    protected boolean requiresAuthentication(final HttpServletRequest request,
            final HttpServletResponse response) {
        boolean requiresAuthentication;

        final String userToken = ControllerHelper.getTokenFromRequest(request);
        if (StringUtils.isEmpty(userToken)) {
            requiresAuthentication = true;
        } else {
            final UserData userData = authenticationService.applyToken(userToken);
            if (userData == null) {
                // intruder alert! intruder alert!
                final int code = HttpServletResponse.SC_FORBIDDEN;
                final JsonResponseData jsonData = new JsonResponseData(code, SecurityMessage.SC003);
                final String json = JsonResponseData.toJson(jsonData);
                ControllerHelper.write(HttpServletResponse.SC_OK, json, response, ControllerHelper.APPLICATION_JSON);
            } else {
                final List<GrantedAuthority> authorities = new ArrayList<>();
                final GrantedAuthority authority = new SimpleGrantedAuthority(userData.getRole());
                authorities.add(authority);

                final Authentication authResult =
                        new AnonymousAuthenticationToken(userData.getUserUuid(), userData, authorities);
                SecurityContextHolder.getContext().setAuthentication(authResult);
            }
            // set on thread to be consumed within the CsrfTokenRepository
            TokenThreadLocal.setToken(userToken);
            // set on thread to be used in services
            UserThreadLocal.setUser(userData);

            requiresAuthentication = false;
        }
        return requiresAuthentication;
    }
}
