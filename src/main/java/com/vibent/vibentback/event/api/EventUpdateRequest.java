package com.vibent.vibentback.event.api;

import lombok.Data;

import javax.validation.constraints.Future;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class EventUpdateRequest {
    @Size(min = 1, max = 50)
    private String title;

    @Size(min = 1, max = 500)
    private String description;

    @Future
    private Date startDate;

    @Future
    private Date endDate;
}
