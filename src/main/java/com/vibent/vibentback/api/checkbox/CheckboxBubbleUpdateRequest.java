package com.vibent.vibentback.api.checkbox;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CheckboxBubbleUpdateRequest {
    @NotNull
    @Size(max = 500, min = 1)
    private String title;
}
