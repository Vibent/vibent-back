package com.vibent.vibentback.group.api;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserMembershipResponse {
    private String groupRef;
    private Boolean isAdmin;
}
