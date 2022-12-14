package com.vibent.vibentback.bubble.travel.api;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class TravelRequestRequest {
    @Min(0)
    @NotNull
    private Long bubbleId;

    @Size(min = 1, max = 100)
    private String passByCities;

    @Min(1)
    @NotNull
    private Integer capacity;
}
