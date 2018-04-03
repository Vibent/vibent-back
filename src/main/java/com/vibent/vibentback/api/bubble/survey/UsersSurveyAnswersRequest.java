package com.vibent.vibentback.api.bubble.survey;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Min;

@Data
public class UsersSurveyAnswersRequest {
    @NonNull
    @Min(0)
    private Long surveyAnswerId;
}
