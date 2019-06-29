package com.vibent.vibentback.common.api;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@RequiredArgsConstructor
public class InviteTokenResponse {
    @NotNull
    @Size(min = 1)
    private final String token;
}
