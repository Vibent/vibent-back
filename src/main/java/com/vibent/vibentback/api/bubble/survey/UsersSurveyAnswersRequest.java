package com.vibent.vibentback.api.bubble.survey;

import lombok.Data;
import lombok.NonNull;

@Data
public class UsersSurveyAnswersRequest {
    @NonNull
    private Long surveyAnswerId;
}
