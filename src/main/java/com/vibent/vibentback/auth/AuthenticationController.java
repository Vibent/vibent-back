package com.vibent.vibentback.auth;

import com.vibent.vibentback.api.auth.*;
import com.vibent.vibentback.api.user.DetailledUserResponse;
import com.vibent.vibentback.common.error.VibentError;
import com.vibent.vibentback.common.error.VibentException;
import com.vibent.vibentback.common.util.TokenUtils;
import com.vibent.vibentback.user.User;
import io.jsonwebtoken.Claims;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private final TokenUtils tokenUtils;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, TokenUtils tokenUtils) {
        this.authenticationService = authenticationService;
        this.tokenUtils = tokenUtils;
    }

    @RequestMapping(value = "/login/email", method = RequestMethod.POST)
    public AuthenticationResponse loginEmail(@Valid @RequestBody EmailLoginRequest request) {
        log.info("Attempted login with email {}", request.getEmail());
        String token = this.authenticationService.loginEmail(request);
        return new AuthenticationResponse(token, EXPIRATION_SECONDS );
    }

    @RequestMapping(value = "/login/phone", method = RequestMethod.POST)
    public AuthenticationResponse loginPhone(@Valid @RequestBody PhoneLoginRequest request) {
        log.info("Attempted login with phone number {}", request.getPhoneNumber());
        String token = this.authenticationService.loginPhone(request);
        return new AuthenticationResponse(token, EXPIRATION_SECONDS);
    }

    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    public AuthenticationResponse refresh(@RequestHeader(value = "${vibent.auth.header.key}") String token) {
        Claims claims = this.tokenUtils.validateJWTToken(token);
        String userRef = claims.getSubject();
        // TODO
        return new AuthenticationResponse(null, EXPIRATION_SECONDS);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public DetailledUserResponse register(@Valid @RequestBody RegistrationRequest request) {
        log.info("Registering for email {}", request.getEmail());
        if(request.getEmail() == null && request.getPhoneNumber() == null)
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
}
