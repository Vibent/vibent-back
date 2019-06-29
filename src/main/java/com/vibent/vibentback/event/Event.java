package com.vibent.vibentback.event;

import com.vibent.vibentback.bubble.alimentation.AlimentationBubble;
import com.vibent.vibentback.bubble.checkbox.CheckboxBubble;
import com.vibent.vibentback.bubble.free.FreeBubble;
import com.vibent.vibentback.bubble.location.LocationBubble;
import com.vibent.vibentback.bubble.planning.PlanningBubble;
import com.vibent.vibentback.bubble.survey.SurveyBubble;
import com.vibent.vibentback.bubble.travel.TravelBubble;
import com.vibent.vibentback.common.permission.Permissible;
import com.vibent.vibentback.event.participation.EventParticipation;
import com.vibent.vibentback.user.User;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@ToString(of = {"id", "ref", "title", "description", "startDate", "endDate", "deleted"})
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@SQLDelete(sql = "UPDATE event SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Event implements Serializable, Permissible {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String ref;

    @NonNull
    private String title;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User creator;

    @NonNull
    private String description;

    @NonNull
    private Date startDate;

    private Date endDate;

    @Column(updatable = false)
    private boolean deleted;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "event", cascade = CascadeType.ALL)
    private Set<EventParticipation> participations = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "event", cascade = CascadeType.ALL)
    private Set<AlimentationBubble> alimentationBubbles = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "event", cascade = CascadeType.ALL)
    private Set<CheckboxBubble> checkboxBubbles = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "event", cascade = CascadeType.ALL)
    private Set<FreeBubble> freeBubbles = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "event", cascade = CascadeType.ALL)
    private Set<LocationBubble> locationBubbles = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "event", cascade = CascadeType.ALL)
    private Set<PlanningBubble> planningBubbles = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "event", cascade = CascadeType.ALL)
    private Set<SurveyBubble> surveyBubbles = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "event", cascade = CascadeType.ALL)
    private Set<TravelBubble> travelBubbles = new HashSet<>();

    @Override
    public boolean canRead(User user) {
        return this.isParticipant(user);
    }

    @Override
    public boolean canWrite(User user) {
        return true;
    }

    @Override
    public boolean canWriteChildren(User user) {
        // If the user can read the event, he can write bubbles
        return this.canRead(user);
    }

    public boolean isParticipant(User user) {
        return this.getParticipations().stream().anyMatch(p -> p.getUser().equals(user));
    }
}