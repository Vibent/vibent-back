package com.vibent.vibentback.api.checkbox;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CheckboxBubbleUpdateRequest {
    @NotNull
    private String title;
}
