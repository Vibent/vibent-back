package com.vibent.vibentback.api.auth;

import com.vibent.vibentback.validate.Email;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class EmailLoginRequest {
    @Email
    @NotNull
    private String email;

    @NotNull
    private String password;
}
