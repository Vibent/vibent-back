package com.vibent.vibentback.auth;

import com.vibent.vibentback.api.auth.AuthenticationResponse;
import com.vibent.vibentback.api.auth.EmailLoginRequest;
import com.vibent.vibentback.api.auth.PhoneLoginRequest;
import com.vibent.vibentback.api.auth.RegistrationRequest;
import com.vibent.vibentback.api.user.DetailledUserResponse;
import com.vibent.vibentback.error.VibentError;
import com.vibent.vibentback.error.VibentException;
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
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthenticationController {

    @Value("${vibent.auth.header.key}")
    private String AUTH_HEADER_KEY;
    @Value("${vibent.auth.expirationSeconds}")
    private long EXPIRATION_SECONDS;

    @NonNull
    private AuthenticationService authenticationService;
    @NonNull
    private TokenUtils tokenUtils;

    @RequestMapping(value = "/login/email", method = RequestMethod.POST)
    public AuthenticationResponse loginEmail(@Valid @RequestBody EmailLoginRequest request) {
        log.info("Attempted login with email {} and password {}", request.getEmail(), request.getPassword());
        String token = this.authenticationService.loginEmail(request);
        return new AuthenticationResponse(token, EXPIRATION_SECONDS );
    }

    @RequestMapping(value = "/login/phone", method = RequestMethod.POST)
    public AuthenticationResponse loginPhone(@Valid @RequestBody PhoneLoginRequest request) {
        log.info("Attempted login with phone number {} and password {}", request.getPhoneNumber(), request.getPassword());
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
        log.info("Registering with info credentials {}", request);
        if(request.getEmail() == null && request.getPhoneNumber() == null)
            throw new VibentException(VibentError.INVALID_BODY);
        User user = authenticationService.register(request);
        return new DetailledUserResponse(user);
    }
}
