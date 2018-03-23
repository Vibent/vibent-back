package com.vibent.vibentback.api.planning;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class PlanningEntryUpdateRequest {

    @NonNull
    private Date start;

    @NonNull
    private Date end;

    @NotNull
    @Size(max = 500, min = 1)
    @NonNull
    private String content;

}
