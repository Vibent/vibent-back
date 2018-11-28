package com.vibent.vibentback.bubble.alimentation.api;

import com.vibent.vibentback.bubble.alimentation.entry.AlimentationEntry;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class AlimentationEntryRequest {
    @Min(0)
    @NotNull
    private Long bubbleId;

    @NotNull
    @Size(min = 1, max = 50)
    private String name;

    private Integer totalRequested;

    private AlimentationEntry.Type type;
}
