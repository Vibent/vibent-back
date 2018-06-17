package com.vibent.vibentback.api.event.participation;

import com.vibent.vibentback.event.participation.EventParticipation.Answer;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserParticipationResponse {
    private String eventRef;
    private Answer answer;
}
