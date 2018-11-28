package com.vibent.vibentback.event.api;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class EventRequest {
    @NotNull
    @Size(min = 1, max = 50)
    private String title;

    @Size(min = 1, max = 500)
    private String description;

    @NotNull
    @Size(min = 36, max = 36)
    private String groupRef;

    @NotNull
    private Date startDate;

    private Date endDate;
}
