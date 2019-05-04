
package com.vibent.vibentback.event.participation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vibent.vibentback.event.Event;
import com.vibent.vibentback.user.User;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@SQLDelete(sql = "UPDATE event_participation SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class EventParticipation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @ManyToOne
    @JsonIgnore
    @PrimaryKeyJoinColumn
    private User user;

    @NonNull
    @ManyToOne
    @JsonIgnore
    @PrimaryKeyJoinColumn
    private Event event;

    @NonNull
    @Enumerated(EnumType.STRING)
    private Answer answer = Answer.UNANSWERED;

    @JsonIgnore
    private boolean isVisible;

    @Column(updatable = false)
    @JsonIgnore
    private boolean deleted;

    public enum Answer {
        YES, NO, MAYBE, UNANSWERED
    }

    @JsonProperty
    public String getUserRef(){
        return user.getRef();
    }

    @JsonProperty
    public String getEventRef(){
        return event.getRef();
    }
}