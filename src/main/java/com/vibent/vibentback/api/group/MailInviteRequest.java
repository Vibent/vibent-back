package com.vibent.vibentback.api.group;

import com.vibent.vibentback.common.validate.Emails;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class MailInviteRequest {
    @Emails
    @NotNull
    @Size(min = 1, max = 10)
    private Set<String> recipients;


    @NotNull
    private String groupRef;
}