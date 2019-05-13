package com.vibent.vibentback.auth.social;

import com.vibent.vibentback.auth.api.AuthenticationResponse;
import com.vibent.vibentback.auth.social.api.SocialLoginRequest;
import com.vibent.vibentback.auth.social.api.SocialUnlinkRequest;
import com.vibent.vibentback.user.User;
import com.vibent.vibentback.user.api.DetailledUserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SocialAuthenticationController {

    private final SocialAuthenticationService socialAuthenticationService;

    @RequestMapping(method = RequestMethod.POST, value = "/auth/social/login")
    public AuthenticationResponse loginSocial(@Valid @RequestBody SocialLoginRequest request) {
        log.info("Attempted login with social provider {}", request.getProvider());
        return socialAuthenticationService.loginSocial(request);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/social/link")
    public DetailledUserResponse linkSocial(@Valid @RequestBody SocialLoginRequest request) {
        log.info("Attempted link social provider {}", request.getProvider());
        User user = socialAuthenticationService.linkSocial(request);
        return new DetailledUserResponse(user);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/social/unlink")
    public DetailledUserResponse unlinkSocial(@Valid @RequestBody SocialUnlinkRequest request) {
        log.info("Attempted unlink social provider {}", request.getProvider());
        User user = socialAuthenticationService.unlinkSocial(request);
        return new DetailledUserResponse(user);
    }
}
