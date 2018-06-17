package com.vibent.vibentback.common.error;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vibent.vibentback.api.error.ErrorBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@Primary
@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {VibentException.class})
    public ResponseEntity<Object> handleVibentException(VibentException ex, WebRequest request) {
        ErrorBody errorBody = new ErrorBody();
        errorBody.setStatus(ex.getError().getStatus().value());
        errorBody.setCode(ex.getError().name().toLowerCase());

        // Set custom message if not null, and if not default VibentError message
        if(ex.getCustomMessage() != null && !ex.getCustomMessage().isEmpty()) {
            errorBody.setMessage(ex.getCustomMessage());
        } else {
            errorBody.setMessage(ex.getError().getMessage());
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
        for(FieldError error : ex.getBindingResult().getFieldErrors()){
            builder.append(error.getField()).append(" ").append(error.getDefaultMessage()).append(". ");
        }
        String errorMessage = null;
        if(!builder.toString().isEmpty()){
            errorMessage = builder.toString();
        }
        return handleVibentException(new VibentException(VibentError.METHOD_ARGUMENT_NOT_VALID, errorMessage), request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("Responding error to request {} with exception {}", request.getDescription(false),  ex.getClass().getSimpleName());
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }
}