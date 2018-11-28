package com.vibent.vibentback.auth.api;

import com.vibent.vibentback.common.validate.Email;
import com.vibent.vibentback.common.validate.Password;
import com.vibent.vibentback.common.validate.PhoneNumber;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class RegistrationRequest {
    @NotNull
    @Password
    private String password;

    @Email
    private String email;

    @PhoneNumber
    private String phoneNumber;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date birthday;

    @NotNull
    @Size(min = 1, max = 50)
    private String firstName;

    @NotNull
    @Size(min = 1, max = 50)
    private String lastName;
}
