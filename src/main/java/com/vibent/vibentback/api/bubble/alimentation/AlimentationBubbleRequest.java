package com.vibent.vibentback.api.bubble.alimentation;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class AlimentationBubbleRequest {
    @NotNull
    @Size(max = 36, min = 36)
    private String eventRef;
}
