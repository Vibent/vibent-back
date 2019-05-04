package com.vibent.vibentback.common.util;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TokenInfo {
    /**
     * ID of concerned entity (groupId, userId, etc)
     */
    private final Long id;
    /**
     * Additional information in token. May be null
     */
    private final String body;
}
