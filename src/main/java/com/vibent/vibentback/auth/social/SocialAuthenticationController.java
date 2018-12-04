package com.vibent.vibentback.auth.social;

import com.vibent.vibentback.auth.api.AuthenticationResponse;
import com.vibent.vibentback.auth.social.api.SocialLoginRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/auth/social")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SocialAuthenticationController {

    @Value("${vibent.auth.expirationSeconds}")
    private long EXPIRATION_SECONDS;

    private final SocialAuthenticationService socialAuthenticationService;

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public AuthenticationResponse loginSocial(@Valid @RequestBody SocialLoginRequest request) {
        log.info("Attempted login with social provider {}", request.getProvider());
        String token = socialAuthenticationService.loginSocial(request);
        return new AuthenticationResponse(token, EXPIRATION_SECONDS);
    }
}
