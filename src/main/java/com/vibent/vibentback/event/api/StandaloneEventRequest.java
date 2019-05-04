package com.vibent.vibentback.event.api;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class StandaloneEventRequest {
    @NotNull
    @Size(min = 1, max = 50)
    private String title;

    @Size(min = 1, max = 500)
    private String description;

    @NotNull
    private Date startDate;

    private Date endDate;
}
