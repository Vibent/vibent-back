package com.vibent.vibentback.bubble.alimentation.api;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class AlimentationBubbleRequest {
    @NotNull
    @Size(min = 36, max = 36)
    private String eventRef;
}
