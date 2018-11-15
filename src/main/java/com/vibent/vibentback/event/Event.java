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
import com.vibent.vibentback.group.GroupT;
import com.vibent.vibentback.group.membership.Membership;
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
@RequiredArgsConstructor
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
    @ManyToOne
    @PrimaryKeyJoinColumn
    private GroupT group;

    @NonNull
    private String title;

    @NonNull
    private String description;

    @NonNull
    private Date startDate;

    private Date endDate;

    @Column(insertable = false, updatable = false)
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
        return this.group.canRead(user);
    }

    @Override
    public boolean canWrite(User user) {
        return this.group.canWrite(user);
    }
}