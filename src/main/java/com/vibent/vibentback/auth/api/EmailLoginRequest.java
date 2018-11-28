package com.vibent.vibentback.auth.api;

import com.vibent.vibentback.common.validate.Email;
import com.vibent.vibentback.common.validate.Password;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class EmailLoginRequest {
    @Email
    @NotNull
    private String email;

    @NotNull
    @Password
    private String password;
}
