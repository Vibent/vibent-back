package com.vibent.vibentback.api.planning;

import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class PlanningBubbleUpdateRequest {

    @NotNull
    @NotNull
    @Size(max = 500, min = 1)
    private String title;

}
