package com.vibent.vibentback.api.travel;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class TravelRequestRequest {
    @NotNull
    @Min(0)
    private Long bubbleId;
    private Integer capacity;
    private Boolean isAttachedToProposal;
}
