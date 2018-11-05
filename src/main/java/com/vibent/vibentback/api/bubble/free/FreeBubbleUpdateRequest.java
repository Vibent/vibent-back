package com.vibent.vibentback.api.bubble.free;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class FreeBubbleUpdateRequest {
    @Size(min = 1, max = 100)
    private String title;

    @Size(min = 1, max = 1000)
    private String content;
}
