package com.vibent.vibentback.common.error;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vibent.vibentback.api.error.ErrorBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@Primary
@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {VibentException.class})
    public ResponseEntity<Object> handleVibentException(VibentException ex, WebRequest request) {
        ErrorBody errorBody = new ErrorBody(ex.getError());

        if (ex.getCustomMessage() != null && !ex.getCustomMessage().isEmpty()) {
            errorBody.setMessage(ex.getCustomMessage());
        }

        String bodyString = null;
        try {
            bodyString = new ObjectMapper().writeValueAsString(errorBody);
        } catch (JsonProcessingException e) {
            bodyString = "Error writing error message";
        }

        return handleExceptionInternal(ex, bodyString, new HttpHeaders(), ex.getError().getStatus(), request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        StringBuilder builder = new StringBuilder();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            builder.append(error.getField()).append(" ").append(error.getDefaultMessage()).append(". ");
        }
        String errorMessage = null;
        if (!builder.toString().isEmpty()) {
            errorMessage = builder.toString();
        }
        return handleVibentException(new VibentException(VibentError.METHOD_ARGUMENT_NOT_VALID, errorMessage), request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (ex instanceof VibentException) {
            VibentException vibentEx = (VibentException) (ex);
            log.error("Responding error to request {} with exception {} : {}, {}",
                    request.getDescription(false),
                    ex.getClass().getSimpleName(),
                    vibentEx.getError().toString(),
                    vibentEx.getOriginalException() != null ? vibentEx.getOriginalException().getMessage() : "");
        } else {
            log.error("Responding error to request {} with exception {}", request.getDescription(false), ex.getClass().getSimpleName());
        }
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }
}