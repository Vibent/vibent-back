package com.vibent.vibentback.api.alimentation;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class AlimentationBringRequest {
    @NotNull
    @Min(0)
    private Long entryId;
    @Min(1)
    private Integer quantity;
}
