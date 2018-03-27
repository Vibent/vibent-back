package com.vibent.vibentback.api.free;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class FreeBubbleUpdateRequest {
    @NotNull
    @Size(max = 100, min = 1)
    private String title;

    @Size(max = 1000, min = 1)
    private String content;
}
