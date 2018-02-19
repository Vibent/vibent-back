package com.vibent.vibentback.api.bubble.survey;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SurveyBubbleUpdateReq {
    @NotNull
    private String title;
}
