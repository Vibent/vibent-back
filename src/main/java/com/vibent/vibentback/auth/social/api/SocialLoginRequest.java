package com.vibent.vibentback.auth.social.api;

import com.vibent.vibentback.auth.social.provider.Provider;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class SocialLoginRequest {

    @Size(min = 1)
    private String idToken;

    @Size(min = 1)
    private String authToken;

    @NotNull
    private Provider provider;
}
