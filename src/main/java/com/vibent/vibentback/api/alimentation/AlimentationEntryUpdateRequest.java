package com.vibent.vibentback.api.alimentation;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class AlimentationEntryUpdateRequest {
    @NotNull
    @Size(max = 64, min = 1)
    private String name;
    private Integer totalRequested;
}
