package com.vibent.vibentback.api.alimentation;

import com.vibent.vibentback.bubble.alimentation.entry.AlimentationEntry;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
public class AlimentationEntryUpdateRequest {
    @Size(max = 64, min = 1)
    private String name;

    @Min(1)
    private Integer totalRequested;

    private AlimentationEntry.Type type;
}
