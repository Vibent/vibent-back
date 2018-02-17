package com.vibent.vibentback.api.bubble.alimentation;

import com.vibent.vibentback.bubble.alimentation.entry.AlimentationEntry;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class AlimentationEntryReq {

    @NotNull
    private Long bubbleId;
    @NotNull
    private String name;
    @Min(0)
    private Integer totalRequested = 0;
    @Min(0)
    private Integer totalCurrent = 0;
    private AlimentationEntry.Type type = AlimentationEntry.Type.Other;
}

