package com.vibent.vibentback.api.bubble.travel;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class TravelBubbleRequest {
    @NotNull
    @Size(min = 36, max = 36)
    private String eventRef;
}
