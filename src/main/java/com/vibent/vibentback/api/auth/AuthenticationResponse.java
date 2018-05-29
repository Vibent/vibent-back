package com.vibent.vibentback.api.auth;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;
import java.util.Date;


@Data
public class AuthenticationResponse {

    @NonNull
	private String token;

    @NonNull
    private long expiresInSeconds;
}
