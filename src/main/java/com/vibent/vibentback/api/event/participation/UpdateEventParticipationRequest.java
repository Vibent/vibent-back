
package com.vibent.vibentback.api.event.participation;

import com.vibent.vibentback.event.participation.EventParticipation;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateEventParticipationRequest {
    private EventParticipation.Answer answer;
    private Boolean isVisible;
}