package com.vibent.vibentback.group.api;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class MembershipResponse {
    private final String userRef;
    private final String groupRef;
    private final Boolean isAdmin;
}
