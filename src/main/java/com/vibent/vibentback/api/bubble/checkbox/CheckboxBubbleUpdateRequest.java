package com.vibent.vibentback.api.bubble.checkbox;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CheckboxBubbleUpdateRequest {
    @NotNull
    @Size(min = 1, max = 500)
    private String title;
}
