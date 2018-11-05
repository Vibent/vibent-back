package com.vibent.vibentback.api.user;

import com.vibent.vibentback.common.validate.Email;
import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class EmailChangeRequest {
    @Email
    @NotNull
    private String email;
}
