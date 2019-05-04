package com.vibent.vibentback.auth.social.api;

import com.vibent.vibentback.auth.social.provider.Provider;
import lombok.Data;

@Data
public class SocialLoginDetails {

    Provider provider;

    String providerId;

    String firstName;

    String lastName;

    String email;
}
