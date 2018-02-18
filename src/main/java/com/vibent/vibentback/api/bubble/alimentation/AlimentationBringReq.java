package com.vibent.vibentback.api.bubble.alimentation;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class AlimentationBringReq {

    @NotNull
    private Long entryId;
    @NotNull
    @Min(0)
    private Integer quantity;
}
