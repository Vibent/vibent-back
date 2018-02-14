package com.vibent.vibentback.error;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.http.HttpStatus;

/**
 * 200 - OK
 * 201 - Created
 * 400 - Bad Request : Si la syntaxe de la requête n'est pas correcte
 * 401 - Unauthorized : Either isn't logged in or is incorrectly logged in - the client must retry logging in
 * 403 - Forbidden : The user is correctly logged in but doesn't have access to the requested data
 * 404 - Not Found
 * 500 - Internal Server Error
 */
@JsonSerialize(using = VibentErrorSerializer.class)
public enum VibentError {
    USER_NOT_FOUND("user-not-found", HttpStatus.NOT_FOUND, "The requested user could not be found"),
    USER_CANT_CREATE("user-cant-create", HttpStatus.INTERNAL_SERVER_ERROR, "User could not be created"),
    UNKNOWN("unknown", HttpStatus.INTERNAL_SERVER_ERROR, "An unknown error has occured");

    private final String code;
    private final HttpStatus status;
    private final String message;

    VibentError(String code, HttpStatus status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "VibentError{code='" + code + ", status=" + status + ", message='" + message + '}';
    }
}
