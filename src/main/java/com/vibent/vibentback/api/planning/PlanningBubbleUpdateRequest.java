package com.vibent.vibentback.api.planning;

import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class PlanningBubbleUpdateRequest {

    @NotNull
    private String title;

}
