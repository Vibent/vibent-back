package com.vibent.vibentback.auth.api;

import lombok.Data;
import lombok.NonNull;

@Data
public class MailConfirmationResponse {
    @NonNull
    private String email;
}
