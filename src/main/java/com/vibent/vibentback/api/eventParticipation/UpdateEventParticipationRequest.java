
package com.vibent.vibentback.api.eventParticipation;

import com.vibent.vibentback.eventParticipation.EventParticipation;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateEventParticipationRequest {
    private EventParticipation.Answer answer;
    private Boolean isVisible;
}