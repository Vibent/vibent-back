package com.vibent.vibentback.api.auth;

import lombok.Data;
import lombok.NonNull;


@Data
public class AuthenticationResponse {
    @NonNull
    private String token;

    @NonNull
    private Long expiresInSeconds;
}
