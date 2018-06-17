package com.vibent.vibentback.api.bubble.planning;

import lombok.Data;

import javax.validation.constraints.Future;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class PlanningEntryUpdateRequest {

    @Future
    private Date start;

    @Future
    private Date end;

    @Size(max = 500, min = 1)
    private String content;
}
