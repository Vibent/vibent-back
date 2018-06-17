package com.vibent.vibentback.api.bubble.free;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class FreeBubbleRequest {
    @NotNull
    @Size(max = 36, min = 36)
    private String eventRef;
    @NotNull
    @Size(max = 100, min = 1)
    private String title;

    @Size(max = 1000, min = 1)
    private String content;
}
