package com.vibent.vibentback.api.survey;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class SurveyAnswerRequest {
    @NotNull
    @Min(0)
    private Long surveyOptionId;
}
