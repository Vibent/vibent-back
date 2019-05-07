package com.vibent.vibentback.group.api;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserMembershipRequestResponse {
    private final String groupRef;
}
