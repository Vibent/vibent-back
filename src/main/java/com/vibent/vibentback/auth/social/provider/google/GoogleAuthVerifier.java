package com.vibent.vibentback.auth.social.provider.google;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.vibent.vibentback.auth.social.SocialVerifier;
import com.vibent.vibentback.auth.social.api.SocialLoginDetails;
import com.vibent.vibentback.auth.social.api.SocialLoginRequest;
import com.vibent.vibentback.common.error.VibentError;
import com.vibent.vibentback.common.error.VibentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import static com.vibent.vibentback.auth.social.provider.Provider.GOOGLE;

@Slf4j
@Service
public class GoogleAuthVerifier implements SocialVerifier {

    private GoogleIdTokenVerifier verifier;

    @Autowired
    public GoogleAuthVerifier(@Value("${vibent.social.google.clientId}") String clientId) throws GeneralSecurityException, IOException {
        verifier = new GoogleIdTokenVerifier.Builder(
                GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance())
                .setAudience(Collections.singletonList(clientId))
                .build();
    }

    public SocialLoginDetails login(SocialLoginRequest request) {
        if (request.getIdToken() == null) {
            throw new VibentException(VibentError.SOCIAL_TOKEN_NOT_FOUND);
        }
        try {
            return getDetails(request.getIdToken());
        } catch (Exception e) {
            throw new VibentException(VibentError.SOCIAL_AUTHENTICATION_FAILED, e);
        }
    }

    private SocialLoginDetails getDetails(String idTokenReq) throws GeneralSecurityException, IOException {
        GoogleIdToken idToken = verifier.verify(idTokenReq);
        GoogleIdToken.Payload payload = idToken.getPayload();
        SocialLoginDetails details = new SocialLoginDetails();
        details.setProvider(GOOGLE);
        details.setProviderId(payload.getSubject());
        details.setEmail(payload.getEmail());
        details.setFirstName((String) payload.get("given_name"));
        details.setLastName((String) payload.get("family_name"));
        return details;
    }
}
