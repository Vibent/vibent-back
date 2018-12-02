package com.vibent.vibentback.common.error;

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
public enum VibentError {
    // Model level errors
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "The requested user could not be found"),
    USER_CANT_CREATE(HttpStatus.INTERNAL_SERVER_ERROR, "The user could not be created"),
    USER_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "The requested email has already be assigned to a different user"),
    USER_EMAIL_NOT_CONFIRMED(HttpStatus.UNAUTHORIZED, "The user hasn't confirmed his email yet"),
    INVALID_CONFIRMED_EMAIL(HttpStatus.UNAUTHORIZED, "The confirmed email does not match the user"),
    USER_NO_EMAIL_OR_PHONE(HttpStatus.UNAUTHORIZED, "An email or phone number must be specified at registration"),

    GROUP_NOT_FOUND(HttpStatus.NOT_FOUND, "The requested group could not be found"),
    MEMBERSHIP_NOT_FOUND(HttpStatus.NOT_FOUND, "The requested membership could not be found"),
    MEMBERSHIP_REQUEST_NOT_FOUND(HttpStatus.NOT_FOUND, "The requested membership request could not be found"),
    USER_ALREADY_PART_OF_GROUP(HttpStatus.BAD_REQUEST, "The requested membership is to a group the user is already a part of"),


    EVENT_NOT_FOUND(HttpStatus.NOT_FOUND, "The requested event could not be found"),
    EVENT_DATE_INVALID(HttpStatus.BAD_REQUEST, "An event's end date must be after its start date, and it's start date must be after the current date"),

    EVENT_PARTICIPATION_NOT_FOUND(HttpStatus.NOT_FOUND, "The requested event participation could not be found"),
    EVENT_PARTICIPATION_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "There should be no event participation duplicates"),

    BUBBLE_NOT_FOUND(HttpStatus.NOT_FOUND, "The requested bubble can not be found"),

    ENTRY_NOT_FOUND(HttpStatus.NOT_FOUND, "The requested response can not be found"),
    BRING_NOT_FOUND(HttpStatus.NOT_FOUND, "The requested bring can not be found"),
    BRING_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "There is already a bring for the user and entry"),

    TRAVEL_PROPOSAL_NOT_FOUND(HttpStatus.NOT_FOUND, "The requested travel proposal can not be found"),
    TRAVEL_PROPOSAL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "The connected user has already created a travel proposal for this event"),
    TRAVEL_REQUEST_NOT_FOUND(HttpStatus.NOT_FOUND, "The requested travel request can not be found"),
    TRAVEL_REQUEST_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "The connected user has already created a travel request for this event"),
    TRAVEL_REQUEST_AND_PROPOSAL_INCOMPATIBLE(HttpStatus.BAD_REQUEST, "The given request and proposal belong to different items and cannot be attached/detached"),
    TRAVEL_PROPOSAL_CAPACITY_SURPASSED(HttpStatus.BAD_REQUEST, "The given request's capacity is too big for the proposal"),

    OPTION_NOT_FOUND(HttpStatus.NOT_FOUND, "The requested option can not be found"),
    ANSWER_NOT_FOUND(HttpStatus.NOT_FOUND, "The requested answer can not be found"),
    ANSWER_ALREADY_CREATED(HttpStatus.NOT_FOUND, "The requested answer is already created for the created user"),

    // Authentication errors
    AUTHENTICATION_FAILED(HttpStatus.UNAUTHORIZED, "The username / password combination does not exist"),
    SOCIAL_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "The social authentication token (authToken or idToken depending on provider) was not found"),
    SOCIAL_AUTHENTICATION_FAILED(HttpStatus.UNAUTHORIZED, "The social authentication failed"),
    SOCIAL_PROVIDER_UNKNOWN(HttpStatus.UNAUTHORIZED, "The requested social provider is unknown"),

    // Token errors
    MALFORMED_TOKEN(HttpStatus.UNAUTHORIZED, "The provided token is malformed"),
    NO_TOKEN(HttpStatus.UNAUTHORIZED, "The token is either not provided or empty is expired"),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "The provided token is expired"),
    UNSUPPORTED_TOKEN(HttpStatus.UNAUTHORIZED, "The format or configuration of the provided token is invalid"),
    INVALID_TOKEN_SIGNATURE(HttpStatus.UNAUTHORIZED, "The provided token's signature does not come from Vibent"),
    ILLEGAL_TOKEN_ARGUMENT(HttpStatus.UNAUTHORIZED, "The provided token has an illegal argument"),
    ILLEGAL_TOKEN_ISSUER(HttpStatus.UNAUTHORIZED, "The provided token was not issued by Vibent"),
    FAILED_TO_CREATE_TOKEN(HttpStatus.INTERNAL_SERVER_ERROR, "The token creation has failed"),
    WRONG_TOKEN_TYPE(HttpStatus.INTERNAL_SERVER_ERROR, "The provided token is of the wrong type"),


    // Image
    GET_IMAGE_FAILED(HttpStatus.NOT_FOUND, "The requested image could not be recovered"),
    UPLOAD_IMAGE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "The requested image could not be uploaded"),


    // General errors
    INVALID_BODY(HttpStatus.BAD_REQUEST, "The request body is badly formed, please check API documentation"),
    PROTECTED_SERIALIZATION_EXCEPTION(HttpStatus.UNAUTHORIZED, "There was an attempt to serialize or deserialize a protected object"),
    UNKNOWN(HttpStatus.INTERNAL_SERVER_ERROR, "An unknown error has occured"),
    NOT_IMPLEMENTED(HttpStatus.INTERNAL_SERVER_ERROR, "A non implemented function was called"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "An internal error within the service has occured"),
    ILLOGICAL_PERMISSION(HttpStatus.INTERNAL_SERVER_ERROR, "An internal error within the service has occured due to a illogical permission being checked"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "The user is not allowed to access this resource"),

    // Spring error
    HTTP_MESSAGE_NOT_READABLE(HttpStatus.BAD_REQUEST, "Message not readable - probably because of a missing or malformed body"),
    METHOD_ARGUMENT_NOT_VALID(HttpStatus.BAD_REQUEST, "Argument not valid - probably because the body validation failed. Please check the API documentation"),
    UNSUPPORTED_ENCODING_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "The server could not encode a value");
    
    private final HttpStatus status;
    private final String defaultMessage;

    VibentError(HttpStatus status, String defaultMessage) {
        this.status = status;
        this.defaultMessage = defaultMessage;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    @Override
    public String toString() {
        return "VibentError{status=" + status + ", defaultMessage='" + defaultMessage + '}';
    }
}
