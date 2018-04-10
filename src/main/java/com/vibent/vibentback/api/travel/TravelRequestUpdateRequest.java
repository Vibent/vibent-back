package com.vibent.vibentback.api.travel;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class TravelRequestUpdateRequest {
    @NotNull
    @Min(1)
    private Integer capacity;
    private Boolean isAttachedToProposal;
}
