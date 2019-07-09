package com.vibent.vibentback.bubble.planning.api;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class PlanningBubbleRequest {
    @NotNull
    @Size(min = 36, max = 36)
    private String eventRef;
}
