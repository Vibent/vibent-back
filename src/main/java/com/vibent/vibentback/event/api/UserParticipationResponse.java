package com.vibent.vibentback.event.api;

import com.vibent.vibentback.event.participation.EventParticipation.Answer;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserParticipationResponse {
    private final String eventRef;
    private final Answer answer;
}
