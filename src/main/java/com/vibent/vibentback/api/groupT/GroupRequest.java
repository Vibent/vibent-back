package com.vibent.vibentback.api.groupT;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class GroupRequest {
    @NotNull
    @Size(min = 1, max = 64)
    private String name;

    @Size(min = 1, max = 500)
    private String description;

    @NotNull
    private Boolean allAdmins;
}
