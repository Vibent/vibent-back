package com.vibent.vibentback.api.auth;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PhoneLoginRequest {
    @NotNull
    private String phoneNumber;

    @NotNull
    private String password;
}
