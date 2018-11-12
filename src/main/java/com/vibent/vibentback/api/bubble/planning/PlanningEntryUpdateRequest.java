package com.vibent.vibentback.api.bubble.planning;

import lombok.Data;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class PlanningEntryUpdateRequest {
    @Future
    private Date start;

    @Future
    private Date end;

    @Size(min = 1, max = 500)
    private String content;
    
    Boolean hasTime;
}
