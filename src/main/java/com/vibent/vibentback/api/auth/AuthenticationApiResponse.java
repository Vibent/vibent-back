package com.vibent.vibentback.api.auth;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.NonNull;


@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AuthenticationApiResponse {

    @NonNull
    private String accessToken;

    @NonNull
    private String tokenType = "Bearer";

    @NonNull
    private Long expiresIn;

    @NonNull
    private String refreshToken = "NOT_IMPLEMENTED";
}
