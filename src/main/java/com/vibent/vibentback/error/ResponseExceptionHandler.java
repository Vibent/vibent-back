package com.vibent.vibentback.error;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {VibentException.class})
    protected ResponseEntity<Object> handleVibentError(VibentException ex, WebRequest request) throws JsonProcessingException {
        String bodyOfResponse = new ObjectMapper().writeValueAsString(ex.getError());
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), ex.getError().getStatus(), request);
    }

    /** TODO figure out how to deal with spring exceptions
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if(! (ex instanceof VibentException))
            body = "{\"code\":\""+ VibentError.UNKNOWN.getCode() + "\",\"status\":\""+ status + "\",\"message\":\""+ VibentError.UNKNOWN.getMessage() + " \"}";
        return new ResponseEntity(body, headers, status);
    }
    */
}