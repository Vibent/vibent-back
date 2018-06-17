package com.vibent.vibentback.api.group;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class GroupUpdateRequest {
    @Size(min = 1, max = 64)
    private String name;

    @Size(min = 1, max = 500)
    private String description;

    private Boolean allAdmins;
}
