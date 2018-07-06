package com.vibent.vibentback.api.auth;

import lombok.Data;
import lombok.NonNull;

@Data
public class MailConfirmationResponse {

    @NonNull
    private String email;

}
