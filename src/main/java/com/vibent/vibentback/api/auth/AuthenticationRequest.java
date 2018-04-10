package com.vibent.vibentback.api.auth;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AuthenticationRequest {
    @NotNull
    private String username;

    @NotNull
    private String password;
}
