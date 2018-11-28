package com.vibent.vibentback.bubble.planning.api;

import lombok.Data;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class PlanningEntryRequest {
    @NotNull
    @Min(0)
    private Long bubbleId;

    @Future
    @NotNull
    private Date start;

    @Future
    private Date end;

    @NotNull
    Boolean hasTime;

    @NotNull
    @Size(min = 1, max = 500)
    private String content;
}
