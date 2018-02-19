package com.vibent.vibentback.api.bubble.survey;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SurveyBubbleReq {
    @NotNull
    private String eventRef;
    @NotNull
    private String title;
}
