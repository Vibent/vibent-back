package com.vibent.vibentback.api.auth;

import com.vibent.vibentback.common.validate.Email;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PasswordResetRequest {
    @Email
    @NotNull
    private String email;
}
