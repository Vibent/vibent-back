package com.vibent.vibentback.auth;

import com.vibent.vibentback.auth.api.*;
import com.vibent.vibentback.common.error.VibentError;
import com.vibent.vibentback.common.error.VibentException;
import com.vibent.vibentback.user.User;
import com.vibent.vibentback.user.api.DetailledUserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @RequestMapping(method = RequestMethod.POST, value = "/login/api", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public AuthenticationApiResponse loginApi(@RequestParam MultiValueMap<String, String> request) {
        log.info("Attempted api for user email {} ", request.getFirst("client_id"));
        return new AuthenticationApiResponse(this.authenticationService.loginApi(request));
    }

    @RequestMapping(value = "/login/email", method = RequestMethod.POST)
    public AuthenticationResponse loginEmail(@Valid @RequestBody EmailLoginRequest request) {
        log.info("Attempted login with email {}", request.getEmail());
        return this.authenticationService.loginEmail(request);
    }

    @RequestMapping(value = "/login/phone", method = RequestMethod.POST)
    public AuthenticationResponse loginPhone(@Valid @RequestBody PhoneLoginRequest request) {
        log.info("Attempted login with phone number {}", request.getPhoneNumber());
        return this.authenticationService.loginPhone(request);
    }

    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    public AuthenticationResponse refresh(@RequestHeader(value = "${vibent.auth.header.key}") String token) {
        throw new VibentException(VibentError.NOT_IMPLEMENTED);
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
