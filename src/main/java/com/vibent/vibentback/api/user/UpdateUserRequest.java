package com.vibent.vibentback.api.user;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String firstName;
    private String lastName;
    private String password;
}
