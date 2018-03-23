package com.vibent.vibentback.api.bubble.survey;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class SurveyBubbleRequest {
    @NotNull
    @Size(max = 36, min = 36)
    private String eventRef;
    @NotNull
    @Size(max = 500, min = 1)
    @NotNull
    private String title;
}
