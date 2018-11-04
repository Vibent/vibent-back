package com.vibent.vibentback.api.auth;

import com.vibent.vibentback.common.validate.Password;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ValidatePasswordResetRequest {
    @NotNull
    private String token;

    @NotNull
    @Password
    private String newPassword;
}
