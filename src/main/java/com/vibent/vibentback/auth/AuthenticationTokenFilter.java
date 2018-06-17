package com.vibent.vibentback.auth;

import com.vibent.vibentback.common.error.ResponseExceptionHandler;
import com.vibent.vibentback.common.error.VibentError;
import com.vibent.vibentback.common.error.VibentException;
import com.vibent.vibentback.common.util.TokenUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.security.sasl.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.function.BiConsumer;

@Slf4j
public class AuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {

    @Value("${vibent.auth.header.key}")
    private String AUTH_HEADER_KEY;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    ResponseExceptionHandler responseExceptionHandler;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("Passing though filter {}", this.getClass().getName());

        tokenUtils = WebApplicationContextUtils
                .getRequiredWebApplicationContext(this.getServletContext())
                .getBean(TokenUtils.class);
        userDetailsService = WebApplicationContextUtils
                .getRequiredWebApplicationContext(this.getServletContext())
                .getBean(UserDetailsService.class);

        HttpServletResponse resp = (HttpServletResponse) response;
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE, PATCH");
        resp.setHeader("Access-Control-Max-Age", "3600");
        resp.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, " + AUTH_HEADER_KEY);


        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String authToken = httpRequest.getHeader(AUTH_HEADER_KEY);


        // Throws exception if signature is invalid, not issued from Vibent, expired, etc
        try {
            Claims claims = this.tokenUtils.validateJWTToken(authToken);
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
    private void handleError(Exception e, ServletRequest request, ServletResponse response ){
        try {
            if (!(e instanceof VibentException)) {
                e = new VibentException(VibentError.UNKNOWN);
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

