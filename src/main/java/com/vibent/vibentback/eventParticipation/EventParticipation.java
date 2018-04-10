
package com.vibent.vibentback.eventParticipation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vibent.vibentback.event.Event;
import com.vibent.vibentback.groupT.GroupT;
import com.vibent.vibentback.user.User;
import lombok.*;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@RequiredArgsConstructor
public class EventParticipation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @ManyToOne
    @PrimaryKeyJoinColumn
    private User user;

    @NonNull
    @ManyToOne
    @PrimaryKeyJoinColumn
    private Event event;

    @NonNull
    @Enumerated(EnumType.STRING)
    private Answer answer = Answer.UNANSWERED;

    @JsonIgnore
    private boolean isVisible;

    public enum Answer {
        YES, NO, MAYBE, UNANSWERED
    }

}