package com.vibent.vibentback.api.bubble.location;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class LocationBubbleUpdateRequest {

    @NotNull
    @Size(max = 255, min = 1)
    private String coord;

}
