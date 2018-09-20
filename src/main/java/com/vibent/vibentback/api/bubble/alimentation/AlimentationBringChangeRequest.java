package com.vibent.vibentback.api.bubble.alimentation;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class AlimentationBringChangeRequest {
    @NotNull
    @Min(0)
    private Long entryId;

    @NotNull
    private Integer quantity;
}
