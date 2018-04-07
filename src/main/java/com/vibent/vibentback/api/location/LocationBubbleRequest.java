package com.vibent.vibentback.api.location;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class LocationBubbleRequest {

    @NotNull
    @Size(max = 36, min = 36)
    private String eventRef;

    @NotNull
    @Size(max = 255, min = 1)
    private String coord;

}
