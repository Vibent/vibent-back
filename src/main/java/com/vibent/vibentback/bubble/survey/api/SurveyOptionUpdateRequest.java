package com.vibent.vibentback.bubble.survey.api;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class SurveyOptionUpdateRequest {
    @NotNull
    @Size(min = 1, max = 500)
    private String content;
}
