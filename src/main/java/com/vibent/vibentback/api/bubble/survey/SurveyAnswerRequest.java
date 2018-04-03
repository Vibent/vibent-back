package com.vibent.vibentback.api.bubble.survey;


import lombok.Data;
import lombok.NonNull;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class SurveyAnswerRequest {
    @NotNull
    @Min(0)
    private Long bubbleId;

    @Size(max = 500, min = 1)
    @NonNull
    private String content;
}
