package com.vibent.vibentback.distributionlist.api;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class DistributionListInviteRequest {
    @Min(1)
    @NotNull
    private Long distributionListId;


    @NotNull
    @Size(min = 36, max = 36)
    private String eventRef;
}
