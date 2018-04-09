package com.vibent.vibentback.api.travel;

import lombok.Data;

@Data
public class TravelRequestUpdateRequest {
    private Integer capacity;
    private Boolean isAttachedToProposal;
}
