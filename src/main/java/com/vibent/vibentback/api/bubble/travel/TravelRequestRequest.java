package com.vibent.vibentback.api.bubble.travel;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class TravelRequestRequest {
    @Min(0)
    @NotNull
    private Long bubbleId;

    @Min(1)
    @NotNull
    private Integer capacity;
}
