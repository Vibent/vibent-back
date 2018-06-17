package com.vibent.vibentback.api.bubble.planning;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class PlanningEntryRequest {

    @NotNull
    @Min(0)
    private Long bubbleId;

    @NonNull
    private Date start;

    @NonNull
    private Date end;

    @NonNull
    @NotNull
    @Size(max = 500, min = 1)
    private String content;
}
