package com.vibent.vibentback.api.group.membership;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcceptGroupMembershipRequestRequest {
    @NotNull
    private String userRef;
    @NotNull
    private String groupRef;
    private boolean isAdmin;
}
