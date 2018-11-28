package com.vibent.vibentback.group.api;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class GroupUpdateRequest {
    @Size(min = 1, max = 50)
    private String name;

    @Size(min = 1, max = 500)
    private String description;

    private Boolean allAdmins;
}
