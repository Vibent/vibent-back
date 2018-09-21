package com.vibent.vibentback.api.bubble.survey;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class SurveyBubbleUpdateRequest {
    @NotNull
    @Size(max = 500, min = 1)
    private String title;
}
