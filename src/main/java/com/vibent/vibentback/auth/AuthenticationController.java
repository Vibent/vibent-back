package com.vibent.vibentback.auth;

import com.vibent.vibentback.MockService;
import com.vibent.vibentback.api.auth.AuthenticationRequest;
import com.vibent.vibentback.api.auth.AuthenticationResponse;
import com.vibent.vibentback.api.auth.RegistrationRequest;
import com.vibent.vibentback.api.auth.RegistrationResponse;
import com.vibent.vibentback.error.VibentError;
import com.vibent.vibentback.error.VibentException;
import com.vibent.vibentback.user.User;
import com.vibent.vibentback.user.UserService;
import io.jsonwebtoken.Claims;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthenticationController {

    @Value("${vibent.auth.header.key}")
    private String AUTH_HEADER_KEY;

    @NonNull
    private AuthenticationManager authenticationManager;
    @NonNull
    private TokenUtils tokenUtils;
    @NonNull
    private UserService userService;
    @NonNull
    private MockService mockService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public AuthenticationResponse login(@Valid @RequestBody AuthenticationRequest authenticationRequest) {
        log.info("Attempted login with credentials {} and {}", authenticationRequest.getUsername(), authenticationRequest.getPassword());
        try {
            Authentication authentication = this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (AuthenticationException e) {
            throw new VibentException(VibentError.AUTHENTICATION_FAILED);
        }

        String token = this.tokenUtils.createJWTToken(authenticationRequest.getUsername());

        return new AuthenticationResponse(token);
    }

    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    public AuthenticationResponse refresh(@RequestHeader(value = "${vibent.auth.header.key}") String token) {
        Claims claims = this.tokenUtils.validateJWTToken(token);
        String username = claims.getSubject();
        // TODO
        return new AuthenticationResponse(null);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public RegistrationResponse register(@Valid @RequestBody RegistrationRequest request) {
        log.info("Registering with info credentials {}", request);
        if (userService.existsByUsername(request.getUsername())) {
            throw new VibentException(VibentError.USERNAME_ALREADY_EXISTS);
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setLastPasswordReset(new Date());
        user.setRef(UUID.randomUUID().toString());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        mockService.sendConfirmationMail(user);

        return new RegistrationResponse(user.getUsername(), user.getEmail());
    }
}
