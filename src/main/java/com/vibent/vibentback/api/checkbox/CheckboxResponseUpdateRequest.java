package com.vibent.vibentback.api.checkbox;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CheckboxResponseUpdateRequest {
    @NotNull
    @Size(max = 500, min = 1)
    @NonNull
    private String content;
}
