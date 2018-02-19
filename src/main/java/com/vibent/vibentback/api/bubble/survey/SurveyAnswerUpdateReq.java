package com.vibent.vibentback.api.bubble.survey;

import lombok.Data;
import lombok.NonNull;

@Data
public class SurveyAnswerUpdateReq {
    @NonNull
    private String content;
}
