package com.vibent.vibentback.auth.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;


@Data
@AllArgsConstructor
public class AuthenticationResponse {

    @NonNull
    private String token;

    @NonNull
    private Long expiresInSeconds;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date lastLogin;
}
