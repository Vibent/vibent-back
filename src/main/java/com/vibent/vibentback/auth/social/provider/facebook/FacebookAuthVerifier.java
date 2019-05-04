package com.vibent.vibentback.auth.social.provider.facebook;

import com.vibent.vibentback.auth.social.SocialVerifier;
import com.vibent.vibentback.auth.social.api.SocialLoginDetails;
import com.vibent.vibentback.auth.social.api.SocialLoginRequest;
import com.vibent.vibentback.common.error.VibentError;
import com.vibent.vibentback.common.error.VibentException;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Service;

import static com.vibent.vibentback.auth.social.provider.Provider.FACEBOOK;

@Service
public class FacebookAuthVerifier implements SocialVerifier {

    public SocialLoginDetails login(SocialLoginRequest request) {
        if (request.getAuthToken() == null) {
            throw new VibentException(VibentError.SOCIAL_TOKEN_NOT_FOUND);
        }
        try {
            return getDetails(request.getAuthToken());
        } catch (Exception e) {
            throw new VibentException(VibentError.SOCIAL_AUTHENTICATION_FAILED, e);
        }
    }

    private SocialLoginDetails getDetails(String authToken) {
        FacebookTemplate facebook = new FacebookTemplate(authToken);
        String[] fields = {"id", "email", "first_name", "last_name"};
        User userProfile = facebook.fetchObject("me", User.class, fields);
        SocialLoginDetails details = new SocialLoginDetails();
        details.setProvider(FACEBOOK);
        details.setProviderId(userProfile.getId());
        details.setEmail(userProfile.getEmail());
        details.setFirstName(userProfile.getFirstName());
        details.setLastName(userProfile.getLastName());
        return details;
    }
}
