package com.vibent.vibentback.api.alimentation;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class AlimentationEntryUpdateRequest {
    private String name;
    private Integer totalRequested;
}
