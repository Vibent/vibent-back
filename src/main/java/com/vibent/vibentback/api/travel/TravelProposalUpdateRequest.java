package com.vibent.vibentback.api.travel;

import lombok.Data;

@Data
public class TravelProposalUpdateRequest {
    private Integer capacity;
    private String passByCities;
}
