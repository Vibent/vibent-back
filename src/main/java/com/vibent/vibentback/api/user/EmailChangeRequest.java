package com.vibent.vibentback.api.user;

import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class EmailChangeRequest {

    @NotNull
    private String email;
}
