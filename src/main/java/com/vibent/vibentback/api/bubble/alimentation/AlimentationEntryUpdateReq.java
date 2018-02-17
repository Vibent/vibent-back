package com.vibent.vibentback.api.bubble.alimentation;

import com.vibent.vibentback.bubble.alimentation.entry.AlimentationEntry;
import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class AlimentationEntryUpdateReq {
    private String name;
    @Min(0)
    private Integer totalRequested;
    @Min(0)
    private Integer totalCurrent;
    private AlimentationEntry.Type type;
}
