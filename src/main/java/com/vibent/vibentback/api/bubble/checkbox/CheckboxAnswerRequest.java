package com.vibent.vibentback.api.bubble.checkbox;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class CheckboxAnswerRequest {
    @NotNull
    @Min(0)
    private Long optionId;

}
