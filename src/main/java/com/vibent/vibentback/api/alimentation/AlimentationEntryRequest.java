package com.vibent.vibentback.api.alimentation;

import com.vibent.vibentback.bubble.alimentation.entry.AlimentationEntry;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class AlimentationEntryRequest {
    @NotNull
    @Min(0)
    private Long bubbleId;
    @NotNull
    @Size(max = 64, min = 1)
    private String name;
    private Integer totalRequested;
    private AlimentationEntry.Type type;
}
