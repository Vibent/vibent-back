package com.vibent.vibentback.api.bubble.survey;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SurveyBubbleUpdateRequest {
    @NotNull
    private String title;
}
