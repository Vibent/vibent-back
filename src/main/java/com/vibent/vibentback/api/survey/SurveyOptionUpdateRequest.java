package com.vibent.vibentback.api.survey;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class SurveyOptionUpdateRequest {
    @Size(max = 500, min = 1)
    @NotNull
    private String content;
}
