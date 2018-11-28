package com.vibent.vibentback.bubble.travel.api;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class TravelProposalRequest {
    @Min(0)
    @NotNull
    private Long bubbleId;

    @Min(1)
    @NotNull
    private Integer capacity;

    @Size(min = 1, max = 100)
    private String passByCities;
}
