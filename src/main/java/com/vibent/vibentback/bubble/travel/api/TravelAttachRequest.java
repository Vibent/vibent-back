package com.vibent.vibentback.bubble.travel.api;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class TravelAttachRequest {
    @NotNull
    @Min(0)
    private Long proposalId;

    @NotNull
    @Min(0)
    private Long requestId;
}
