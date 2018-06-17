package com.vibent.vibentback.api.group.membership;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MembershipResponse {
    private String userRef;
    private String groupRef;
    private Boolean isAdmin;
}