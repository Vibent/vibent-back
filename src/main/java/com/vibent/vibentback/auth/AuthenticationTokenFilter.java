package com.vibent.vibentback.auth;

import com.vibent.vibentback.common.error.ResponseExceptionHandler;
import com.vibent.vibentback.common.error.VibentError;
import com.vibent.vibentback.common.error.VibentException;
import com.vibent.vibentback.common.util.JWTUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class AuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {

    private static final String BEARER = "Bearer ";
    private String authHeaderKey;
    private JWTUtils JWTUtils;
    private UserDetailsService userDetailsService;
    private ResponseExceptionHandler responseExceptionHandler;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("Passing though filter {}", this.getClass().getName());
        ServletContext servletContext = request.getServletContext();
        JWTUtils = WebApplicationContextUtils
                .getRequiredWebApplicationContext(servletContext)
                .getBean(JWTUtils.class);
        userDetailsService = WebApplicationContextUtils
                .getRequiredWebApplicationContext(servletContext)
                .getBean(UserDetailsService.class);
        responseExceptionHandler = WebApplicationContextUtils
                .getRequiredWebApplicationContext(servletContext)
                .getBean(ResponseExceptionHandler.class);
        authHeaderKey = WebApplicationContextUtils
                .getRequiredWebApplicationContext(servletContext)
                .getEnvironment().getProperty("vibent.auth.header.key");


        HttpServletResponse resp = (HttpServletResponse) response;
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE, PATCH");
        resp.setHeader("Access-Control-Max-Age", "3600");
        resp.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, " + authHeaderKey);

        // Throws exception if signature is invalid, not issued from Vibent, expired, etc
        try {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String authToken = httpRequest.getHeader(authHeaderKey);
            if (authToken == null || !authToken.matches("^Bearer .+")) {
                throw new VibentException(VibentError.NO_TOKEN);
            }
            authToken = authToken.substring(BEARER.length());

            Claims claims = this.JWTUtils.validateUserAuthToken(authToken);
            String userRef = claims.getSubject();

            Authentication authentication = new VibentAuthentication(userRef, null);

            SecurityContextHolder.clearContext();
            SecurityContextHolder.setContext(new SecurityContextImpl(authentication));
            chain.doFilter(request, response);
        } catch (Exception e) {
            handleError(e, request, response);
        }
    }

    /**
     * Method to handle any exceptions thrown during authentication (including failed authentication)
     * It first tries to handle the error using @see com.vibent.vibentback.common.error.ResponseExceptionHandler.
     * If that fails, it attempts to write the exceptions message in the response.
     * If all else fails, it simple returns an empty response with the error code 500.
     */
    private void handleError(Exception e, ServletRequest request, ServletResponse response) {
        try {
            if (!(e instanceof VibentException)) {
                e = new VibentException(VibentError.UNKNOWN, e);
            }
            HttpServletResponse httpResponse = (HttpServletResponse) response;

            ResponseEntity<Object> errorResponse = responseExceptionHandler.handleVibentException((VibentException) e, new ServletWebRequest((HttpServletRequest) request));
            HttpHeaders headers = errorResponse.getHeaders();
            headers.forEach((s, strings) -> {
                for (String headerValue : strings) {
                    httpResponse.addHeader(s, headerValue);
                }
            });
            httpResponse.setStatus(errorResponse.getStatusCodeValue());
            httpResponse.getWriter().write((String) errorResponse.getBody());
            httpResponse.getWriter().flush();
        } catch (Exception e2) {
            try {
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, e2.getMessage());
            } catch (IOException e1) {
                ((HttpServletResponse) response).setStatus(500);
            }
        }
    }

}

