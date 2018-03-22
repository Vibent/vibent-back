package com.vibent.vibentback.api.planning;

import lombok.Data;
import lombok.NonNull;

import java.util.Date;

@Data
public class PlanningEntryUpdateRequest {

    @NonNull
    private Date start;

    @NonNull
    private Date end;

    @NonNull
    private String content;

}
