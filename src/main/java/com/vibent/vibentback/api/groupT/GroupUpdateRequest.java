package com.vibent.vibentback.api.groupT;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class GroupUpdateRequest {
    @Size(min = 1, max = 64)
    private String name;

    private Boolean allAdmins;
}
