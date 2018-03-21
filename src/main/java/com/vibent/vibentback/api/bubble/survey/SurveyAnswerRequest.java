package com.vibent.vibentback.api.bubble.survey;


import lombok.Data;
import lombok.NonNull;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class SurveyAnswerRequest {
    @NotNull
    @Min(0)
    private Long bubbleId;
    @NonNull
    private String content;
}
