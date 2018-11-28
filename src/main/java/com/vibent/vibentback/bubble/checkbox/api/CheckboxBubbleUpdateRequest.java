package com.vibent.vibentback.bubble.checkbox.api;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CheckboxBubbleUpdateRequest {
    @NotNull
    @Size(min = 1, max = 500)
    private String title;
}
