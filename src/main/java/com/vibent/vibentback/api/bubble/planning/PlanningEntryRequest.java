package com.vibent.vibentback.api.bubble.planning;

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
    @NotNull
    private Date end;

    @NotNull
    @Size(max = 500, min = 1)
    private String content;
}
