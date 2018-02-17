package com.vibent.vibentback.api.bubble.alimentation;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class AlimentationBringUpdateReq {
    @NotNull
    @Min(0)
    private Integer quantity;
}
