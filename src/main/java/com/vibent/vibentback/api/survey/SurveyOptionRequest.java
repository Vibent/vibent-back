package com.vibent.vibentback.api.survey;


import lombok.Data;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class SurveyOptionRequest {
    @NotNull
    @Min(0)
    private Long bubbleId;

    @NotNull
    @Size(max = 500, min = 1)
    private String content;
}
