
package com.gitlab.vibent.vibentback.event.EventParticipation;

import lombok.Data;
import lombok.NonNull;
import javax.persistence.*;

@Data
@Entity
public class EventParticipation {

    @NonNull
    private String userRef;
    @NonNull
    private String groupRef;
    @NonNull
    private String answer;
    @NonNull
    private boolean isVisible;

}