package com.vibent.vibentback.bubble.survey.api;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class SurveyAnswerRequest {
    @Min(0)
    @NotNull
    private Long optionId;
}
