package com.vibent.vibentback.distributionlist.api;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SimpleDistributionListResponse {
    private final Long id;
    private final String title;
}