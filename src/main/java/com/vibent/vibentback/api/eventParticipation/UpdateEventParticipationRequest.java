
package com.vibent.vibentback.api.eventParticipation;

import com.vibent.vibentback.eventParticipation.EventParticipation;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class UpdateEventParticipationRequest {
    private EventParticipation.Answer answer;
    private Boolean isVisible;
}