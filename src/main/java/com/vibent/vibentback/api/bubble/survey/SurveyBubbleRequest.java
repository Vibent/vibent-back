package com.vibent.vibentback.api.bubble.survey;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SurveyBubbleRequest {
    @NotNull
    private String eventRef;
    @NotNull
    private String title;
}
