package com.vibent.vibentback.api.bubble.alimentation;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AlimentationBubbleReq {

    @NotNull
    private String eventRef;

}