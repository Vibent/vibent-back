package com.vibent.vibentback.auth.api;

import com.vibent.vibentback.common.validate.Password;
import com.vibent.vibentback.common.validate.PhoneNumber;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PhoneLoginRequest {
    @NotNull
    @PhoneNumber
    private String phoneNumber;

    @NotNull
    @Password
    private String password;
}
