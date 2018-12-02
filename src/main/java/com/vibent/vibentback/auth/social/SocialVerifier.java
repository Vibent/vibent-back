package com.vibent.vibentback.auth.social;

import com.vibent.vibentback.auth.social.api.SocialLoginDetails;
import com.vibent.vibentback.auth.social.api.SocialLoginRequest;

public interface SocialVerifier {

    SocialLoginDetails login(SocialLoginRequest token);
}
