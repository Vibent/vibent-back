package com.vibent.vibentback.api.bubble.travel;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
public class TravelProposalUpdateRequest {
    @Min(1)
    private Integer capacity;
    @Size(min = 1, max = 500)
    private String passByCities;
}
