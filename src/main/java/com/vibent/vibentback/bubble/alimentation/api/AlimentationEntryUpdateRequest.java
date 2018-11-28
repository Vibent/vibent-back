package com.vibent.vibentback.bubble.alimentation.api;

import com.vibent.vibentback.bubble.alimentation.entry.AlimentationEntry;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
public class AlimentationEntryUpdateRequest {
    @Size(min = 1, max = 50)
    private String name;

    @Min(1)
    private Integer totalRequested;

    private AlimentationEntry.Type type;
}
