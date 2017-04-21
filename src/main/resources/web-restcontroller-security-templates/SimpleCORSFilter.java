package com.${companyName}.${productName}.restcontroller.security;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * Servlet filter which allows for cross origin resource allocation.
 *
 * @author ${codeAuthor}.
 */
public class SimpleCORSFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;

        String origin = request.getHeader("Origin");
        if (origin != null) {
            final String urlOrigin = URLEncoder.encode(origin, "UTF-8");
            response.setHeader("Access-Control-Allow-Origin", urlOrigin);
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");

            // The Access-Control-Max-Age header indicates how long the browser may cache the response.
            response.setHeader("Access-Control-Max-Age", "1");
            response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-XSRF-TOKEN, Origin");
        }

        chain.doFilter(req, res);
    }

    @Override
    public void init(final FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }
}
