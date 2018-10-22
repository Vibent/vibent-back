package com.vibent.vibentback.api.auth;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ValidatePasswordResetRequest {

    @NotNull
    private String token;

    @NotNull
    private String newPassword;
}
