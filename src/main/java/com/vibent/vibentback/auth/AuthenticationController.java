package com.vibent.vibentback.auth;

import com.vibent.vibentback.user.api.DetailledUserResponse;
import com.vibent.vibentback.auth.api.*;
import com.vibent.vibentback.common.error.VibentError;
import com.vibent.vibentback.common.error.VibentException;
import com.vibent.vibentback.common.util.JWTUtils;
import com.vibent.vibentback.user.User;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Value("${vibent.auth.header.key}")
    private String AUTH_HEADER_KEY;
    @Value("${vibent.auth.expirationSeconds}")
    private long EXPIRATION_SECONDS;

    private final AuthenticationService authenticationService;
    private final JWTUtils JWTUtils;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, JWTUtils JWTUtils) {
        this.authenticationService = authenticationService;
        this.JWTUtils = JWTUtils;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/login/api", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public AuthenticationApiResponse loginApi(@RequestParam MultiValueMap<String, String> request) {
        log.info("Attempted api for user email {} ", request.getFirst("client_id"));
        String token = this.authenticationService.loginApi(request);
        return new AuthenticationApiResponse(token, EXPIRATION_SECONDS);
    }

    @RequestMapping(value = "/login/email", method = RequestMethod.POST)
    public AuthenticationResponse loginEmail(@Valid @RequestBody EmailLoginRequest request) {
        log.info("Attempted login with email {}", request.getEmail());
        String token = this.authenticationService.loginEmail(request);
        return new AuthenticationResponse(token, EXPIRATION_SECONDS);
    }

    @RequestMapping(value = "/login/phone", method = RequestMethod.POST)
    public AuthenticationResponse loginPhone(@Valid @RequestBody PhoneLoginRequest request) {
        log.info("Attempted login with phone number {}", request.getPhoneNumber());
        String token = this.authenticationService.loginPhone(request);
        return new AuthenticationResponse(token, EXPIRATION_SECONDS);
    }

    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    public AuthenticationResponse refresh(@RequestHeader(value = "${vibent.auth.header.key}") String token) {
        Claims claims = this.JWTUtils.validateJWTToken(token);
        String userRef = claims.getSubject();
        // TODO
        return new AuthenticationResponse(null, EXPIRATION_SECONDS);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public DetailledUserResponse register(@Valid @RequestBody RegistrationRequest request) {
        log.info("Registering for email {}", request.getEmail());
        if (request.getEmail() == null && request.getPhoneNumber() == null)
            throw new VibentException(VibentError.INVALID_BODY);
        User user = authenticationService.register(request);
        return new DetailledUserResponse(user);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/confirmEmail/{token:.+}")
    public MailConfirmationResponse confirmEmail(@PathVariable String token) {
        log.info("Confirming email with token {}", token);
        String email = authenticationService.confirmEmail(token);
        return new MailConfirmationResponse(email);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/passwordReset")
    public void requestPasswordReset(@Valid @RequestBody PasswordResetRequest request) {
        log.info("Request password reset for email {}", request.getEmail());
        authenticationService.requestPasswordReset(request);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/validatePasswordReset")
    public void validateResetPassword(@Valid @RequestBody ValidatePasswordResetRequest request) {
        log.info("Resetting password with token {}", request.getToken());
        authenticationService.validatePasswordReset(request);
    }
}
