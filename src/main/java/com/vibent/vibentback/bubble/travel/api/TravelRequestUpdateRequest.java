package com.vibent.vibentback.bubble.travel.api;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
public class TravelRequestUpdateRequest {
    @Min(1)
    private Integer capacity;

    @Size(min = 1, max = 100)
    private String passByCities;
}
