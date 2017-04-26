package ${topLevelDomain}.${companyName}.${productName}.restcontroller.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * Authenticates that the user is permitted to use the application and establishes the roles for the user.
 *
 * @author ${codeAuthor}.
 */
@Component("RestAuthenticationManager")
public class RestAuthenticationManager implements AuthenticationProvider {
    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
