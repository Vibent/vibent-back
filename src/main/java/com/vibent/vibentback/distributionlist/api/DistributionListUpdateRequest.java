package com.vibent.vibentback.distributionlist.api;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class DistributionListUpdateRequest {

    @Size(min = 1, max = 50)
    private String title;

    @Size(min = 1, max = 500)
    private String description;
}
