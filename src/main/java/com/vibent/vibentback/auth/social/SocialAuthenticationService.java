package com.vibent.vibentback.auth.social;

import com.vibent.vibentback.auth.AuthenticationService;
import com.vibent.vibentback.auth.api.RegistrationRequest;
import com.vibent.vibentback.auth.social.api.SocialLoginDetails;
import com.vibent.vibentback.auth.social.api.SocialLoginRequest;
import com.vibent.vibentback.auth.social.provider.facebook.FacebookAuthVerifier;
import com.vibent.vibentback.auth.social.provider.google.GoogleAuthVerifier;
import com.vibent.vibentback.common.error.VibentError;
import com.vibent.vibentback.common.error.VibentException;
import com.vibent.vibentback.user.User;
import com.vibent.vibentback.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SocialAuthenticationService {

    private final GoogleAuthVerifier googleAuthVerifier;
    private final FacebookAuthVerifier facebookAuthVerifier;
    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final SocialCredentialsRepository credentialsRepository;

    public String loginSocial(SocialLoginRequest request) {
        SocialLoginDetails details = null;
        switch (request.getProvider()) {
            case GOOGLE:
                details = googleAuthVerifier.login(request);
                break;
            case FACEBOOK:
                details = facebookAuthVerifier.login(request);
                break;
            default:
                throw new VibentException(VibentError.SOCIAL_PROVIDER_UNKNOWN);
        }


        String token;
        SocialCredentials credentials = credentialsRepository.findByProviderAndProviderId(request.getProvider(), details.getProviderId());
        // User has already logged in with social provider
        if (credentials != null) {
            log.info("User has already logged in with social provider");
            token = loginExistingCredentials(credentials);

        } else {
            Optional<User> user = userRepository.findByEmail(details.getEmail());

            // User has never logged in with social provider but has account
            if (user.isPresent()) {
                log.info("User has never logged in with social provider but has account");
                token = loginExistingAccount(user.get(), details);
            }

            // User doesn't have an account
            else {
                log.info("User doesn't have an account");
                token = loginNewAccount(details);
            }
        }
        return token;
    }

    public String loginExistingCredentials(SocialCredentials credentials) {
        return authenticationService.createToken(credentials.getUser());
    }

    private String loginExistingAccount(User user, SocialLoginDetails details) {
        SocialCredentials credentials = new SocialCredentials();
        credentials.setUser(user);
        credentials.setProvider(details.getProvider());
        credentials.setProviderId(details.getProviderId());

        user.getCredentials().add(credentials);
        if (user.getFirstName() == null)
            user.setFirstName(details.getFirstName());
        if (user.getLastName() == null)
            user.setLastName(details.getLastName());
        credentialsRepository.save(credentials);
        userRepository.save(user);
        return authenticationService.createToken(user);
    }

    public String loginNewAccount(SocialLoginDetails details) {
        RegistrationRequest registrationRequest = new RegistrationRequest(
                null,
                details.getEmail(),
                null,
                null,
                details.getFirstName(),
                details.getLastName()
        );
        User user = authenticationService.register(registrationRequest);
        loginExistingAccount(user, details);
        return authenticationService.createToken(user);
    }
}
