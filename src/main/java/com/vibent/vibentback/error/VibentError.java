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
    // Model level errors
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "The requested user could not be found"),
    USER_CANT_CREATE(HttpStatus.INTERNAL_SERVER_ERROR, "The user could not be created"),
    USERNAME_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "The requested username has already be assigned to a different user"),

    GROUP_NOT_FOUND(HttpStatus.NOT_FOUND, "The requested group could not be found"),

    EVENT_NOT_FOUND(HttpStatus.NOT_FOUND, "The requested event could not be found"),
    EVENT_DATE_INVALID(HttpStatus.BAD_REQUEST, "An event's end date must be after its start date, and it's start date must be after the current date"),

    EVENT_PARTICIPATION_NOT_FOUND(HttpStatus.NOT_FOUND, "The requested event participation could not be found"),
    EVENT_PARTICIPATION_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "There should be no event participation duplicates"),

    BUBBLE_NOT_FOUND(HttpStatus.NOT_FOUND, "The requested bubble can not be found"),
    BUBBLE_CANT_CREATE(HttpStatus.NOT_FOUND, "Bubble can not be created"),

    ENTRY_NOT_FOUND(HttpStatus.NOT_FOUND, "The requested response can not be found"),
    BRING_NOT_FOUND(HttpStatus.NOT_FOUND, "The requested bring can not be found"),

    TRAVEL_PROPOSAL_NOT_FOUND(HttpStatus.NOT_FOUND, "The requested travel proposal can not be found"),
    TRAVEL_REQUEST_NOT_FOUND(HttpStatus.NOT_FOUND, "The requested travel request can not be found"),

    SURVEY_OPTION_NOT_FOUND(HttpStatus.NOT_FOUND, "The requested response can not be found"),
    USER_ANSWER_NOT_FOUND(HttpStatus.NOT_FOUND, "The requested user response can not be found"),

    // Authentication errors
    AUTHENTICATION_FAILED(HttpStatus.UNAUTHORIZED, "The username / password combination does not exist"),
    AUTH_USER_NOT_FOUND(HttpStatus.UNAUTHORIZED, "The username provided did not match any user"),
    USER_NOT_CONNECTED(HttpStatus.UNAUTHORIZED, "The user must be connected to access this endpoint"),

    // Token errors
    MALFORMED_TOKEN(HttpStatus.UNAUTHORIZED, "The authentication token is malformed"),
    TOKEN_CANNOT_BE_REFRESHED(HttpStatus.UNAUTHORIZED, "The authentication token cannot be refreshed"),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "The authentication token is expired"),
    UNSUPPORTED_TOKEN(HttpStatus.UNAUTHORIZED, "The format or configuration of the token is invalid"),
    INVALID_TOKEN_SIGNATURE(HttpStatus.UNAUTHORIZED, "The token's signature does not come from Vibent"),
    ILLEGAL_TOKEN_ARGUMENT(HttpStatus.UNAUTHORIZED, "The provided token has an illegal argument"),
    ILLEGAL_TOKEN_ISSUE(HttpStatus.UNAUTHORIZED, "The provided token was not issued by Vibent"),




    // General errors
    INVALID_BODY(HttpStatus.BAD_REQUEST, "The request body is badly formed, please check API documentation"),
    PROTECTED_SERIALIZATION_EXCEPTION(HttpStatus.UNAUTHORIZED, "There was an attempt to serialize or deserialize a protected object"),
    UNKNOWN(HttpStatus.INTERNAL_SERVER_ERROR, "An unknown error has occured"),
    NOT_IMPLEMENTED(HttpStatus.INTERNAL_SERVER_ERROR, "A non implemented function was called");

    private final HttpStatus status;
    private final String message;

    VibentError(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "VibentError{status=" + status + ", message='" + message + '}';
    }
}
