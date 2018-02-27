package com.vibent.vibentback.api.alimentation;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class AlimentationEntryRequest {
    @NotNull
    @Min(0)
    private Long bubbleId;
    @NotNull
    private String name;
    private Integer totalRequested;
}
