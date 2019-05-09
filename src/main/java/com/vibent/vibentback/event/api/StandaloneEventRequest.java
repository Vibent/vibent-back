package com.vibent.vibentback.event.api;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

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

    @ApiModelProperty(notes = "If set, every member of group is added to the new event. The event is NOT part of the " +
            "group however and remains standalone")
    private String groupRef;

    @ApiModelProperty(notes = "If set, every user defined will be added to the event")
    private Set<String> invitedUserRefs;
}
