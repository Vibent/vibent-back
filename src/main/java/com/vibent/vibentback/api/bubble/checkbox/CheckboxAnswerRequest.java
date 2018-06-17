package com.vibent.vibentback.api.bubble.checkbox;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Min;

@Data
public class CheckboxAnswerRequest {
    @NonNull
    @Min(0)
    private Long checkboxResponseId;
}
