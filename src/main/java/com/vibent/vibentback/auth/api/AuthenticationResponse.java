package com.vibent.vibentback.auth.api;

import lombok.Data;
import lombok.NonNull;


@Data
public class AuthenticationResponse {
    @NonNull
    private String token;

    @NonNull
    private Long expiresInSeconds;
}
