package com.vibent.vibentback.common.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenInfo {
    /**
     * ID of concerned entity (groupId, userId, etc)
     */
    Long id;
    /**
     * Additional information in token. May be null
     */
    String body;
}
