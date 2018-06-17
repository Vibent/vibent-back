package com.vibent.vibentback.api.group.membership;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GroupMembershipResponse {
    private String userRef;
    private Boolean isAdmin;
}
