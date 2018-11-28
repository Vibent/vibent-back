package com.vibent.vibentback.api.bubble.travel;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class TravelRequestAndAttachRequest {
    @NotNull
    @Min(0)
    private Long proposalId;
    @NotNull
    @Min(1)
    private Integer capacity;
    @Size(min = 1, max = 100)
    private String passByCities;
}
