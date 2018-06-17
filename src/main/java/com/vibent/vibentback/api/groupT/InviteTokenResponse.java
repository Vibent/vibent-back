package com.vibent.vibentback.api.groupT;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class InviteTokenResponse {
    @NotNull
    @Size(min = 1)
    private String token;
}
