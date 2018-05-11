package com.vibent.vibentback.api.eventParticipation;

import com.vibent.vibentback.eventParticipation.EventParticipation.Answer;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventParticipationResponse {
    private String userRef;
    private Answer answer;
}
