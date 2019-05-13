package com.vibent.vibentback.auth.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;


@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AuthenticationApiResponse {

    public AuthenticationApiResponse(AuthenticationResponse authInfo) {
        this.accessToken = authInfo.getToken();
        this.tokenType = "Bearer";
        this.expiresIn = authInfo.getExpiresInSeconds();
        this.refreshToken = "NOT_IMPLEMENTED";
    }

    @NonNull
    private String accessToken;

    @NonNull
    private String tokenType;

    @NonNull
    private Long expiresIn;

    @NonNull
    private String refreshToken;
}
