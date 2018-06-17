package com.vibent.vibentback.api.group.membership;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserMembershipResponse {
    private String groupRef;
    private Boolean isAdmin;
}