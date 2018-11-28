package com.vibent.vibentback.group.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcceptGroupMembershipRequestRequest {
    @NotNull
    @Size(min = 36, max = 36)
    private String userRef;

    @NotNull
    @Size(min = 36, max = 36)
    private String groupRef;

    private boolean isAdmin;
}
