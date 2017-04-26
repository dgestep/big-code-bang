package ${topLevelDomain}.${companyName}.${productName}.restcontroller.security;

import ${topLevelDomain}.${companyName}.${productName}.model.log.LogFactory;
import ${topLevelDomain}.${companyName}.${productName}.model.log.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Used to show the unauthorized access page if someone attempt to enter the application with authorization.
 *
 * @author ${codeAuthor}.
 */
@Component("RestAuthenticationEntryPoint")
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        final Logger logger = LogFactory.getLogger();
        logger.error("Unsuccessful REST authentication for user: " + request.getParameter("username"));

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}
