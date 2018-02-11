
package com.gitlab.vibent.vibentback.event.EventParticipation;

import lombok.Data;
import lombok.NonNull;
import javax.persistence.*;

@Data
@Entity
public class EventParticipation {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String userRef;
    @NonNull
    private String groupRef;
    @NonNull
    private String answer;
    private boolean isVisible;

}