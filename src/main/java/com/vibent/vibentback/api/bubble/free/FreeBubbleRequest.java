package com.vibent.vibentback.api.bubble.free;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class FreeBubbleRequest {
    @NotNull
    @Size(min = 36, max = 36)
    private String eventRef;

    @NotNull
    @Size(min = 1, max = 100)
    private String title;

    @Size(min = 1, max = 1000)
    private String content;
}
