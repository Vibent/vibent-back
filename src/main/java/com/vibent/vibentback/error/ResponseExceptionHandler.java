package com.vibent.vibentback.error;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {VibentException.class})
    protected ResponseEntity<Object> handleVibentError(VibentException ex, WebRequest request) throws JsonProcessingException {
        String bodyOfResponse = new ObjectMapper().writeValueAsString(ex.getError());
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.OK, request);
    }

    /**
     * Default exception handler, should only be invoked if something we don't expect happens
     */
    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<Object> handleUnknownError(Exception ex, WebRequest request) {
        // TODO : activate for prod
        // return handleVibentError(new VibentException(VibentError.UNKNOWN), request);
        // For now we use Springs default
        return handleException(ex, request);
    }
}