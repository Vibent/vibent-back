package com.vibent.vibentback.api.planning;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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
    private String content;
}
