package com.vibent.vibentback.user.api;

import com.vibent.vibentback.common.validate.Password;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class UpdateUserRequest {
    @Size(min = 1, max = 50)
    private String firstName;

    @Size(min = 1, max = 50)
    private String lastName;

    @Password
    private String password;
}
