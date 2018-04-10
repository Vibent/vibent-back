package com.vibent.vibentback.error;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {VibentException.class})
    protected ResponseEntity<Object> handleVibentException(VibentException ex, WebRequest request) throws JsonProcessingException {
        String bodyOfResponse = new ObjectMapper().writeValueAsString(ex.getError());
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), ex.getError().getStatus(), request);
    }
}