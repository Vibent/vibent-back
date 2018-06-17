package com.vibent.vibentback.auth;

import com.vibent.vibentback.common.error.ResponseExceptionHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component(value = "authenticationEntryPoint")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EntryPointUnauthorizedHandler implements AuthenticationEntryPoint {

    ResponseExceptionHandler exceptionHandler;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied");
    }
}
