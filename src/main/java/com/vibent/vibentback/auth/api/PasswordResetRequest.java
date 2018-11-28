package com.vibent.vibentback.auth.api;

import com.vibent.vibentback.common.validate.Email;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PasswordResetRequest {
    @Email
    @NotNull
    private String email;
}
