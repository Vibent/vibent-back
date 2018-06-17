package com.vibent.vibentback.api.bubble.planning;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class PlanningBubbleRequest {
    @NotNull
    @Size(max = 36, min = 36)
    private String eventRef;
    @NotNull
    @Size(max = 500, min = 1)
    private String title;
}
