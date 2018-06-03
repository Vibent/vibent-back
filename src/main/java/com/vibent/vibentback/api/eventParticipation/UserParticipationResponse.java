package com.vibent.vibentback.api.eventParticipation;

import com.vibent.vibentback.eventParticipation.EventParticipation.Answer;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserParticipationResponse {
    private String eventRef;
    private Answer answer;
}
