package com.vibent.vibentback.api.auth;

import com.vibent.vibentback.validate.BCrypt;
import com.vibent.vibentback.validate.Email;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class RegistrationRequest {

    @NotNull
    private String username;

    @NotNull
    @BCrypt
    private String password;

    @Email
    @NotNull
    private String email;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date birthday;

    @NotNull
    @Size(min = 1, max = 64)
    private String firstName;

    @NotNull
    @Size(min = 1, max = 64)
    private String lastName;
}
