package com.vibent.vibentback.common.api;

import com.vibent.vibentback.common.validate.Emails;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class MailInviteIdRequest {
    @Emails
    @NotNull
    @Size(min = 1, max = 10)
    private Set<String> recipients;

    @Min(1)
    @NotNull
    private Long id;
}
