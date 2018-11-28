package com.vibent.vibentback.bubble.alimentation.api;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AlimentationBringUpdateRequest {
    @NotNull
    private Integer quantity;
}
