package com.vibent.vibentback.auth.social.api;

import com.vibent.vibentback.auth.social.provider.Provider;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SocialUnlinkRequest {
    @NotNull
    private Provider provider;
}
