package com.vibent.vibentback.api.groupT;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidateInviteTokenRequest {
    @NotNull
    private String token;
}
