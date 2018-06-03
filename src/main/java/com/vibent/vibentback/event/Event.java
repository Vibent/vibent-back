package com.vibent.vibentback.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vibent.vibentback.api.eventParticipation.EventParticipationResponse;
import com.vibent.vibentback.bubble.alimentation.AlimentationBubble;
import com.vibent.vibentback.bubble.checkbox.CheckboxBubble;
import com.vibent.vibentback.bubble.free.FreeBubble;
import com.vibent.vibentback.bubble.location.LocationBubble;
import com.vibent.vibentback.bubble.planning.PlanningBubble;
import com.vibent.vibentback.bubble.survey.SurveyBubble;
import com.vibent.vibentback.bubble.travel.TravelBubble;
import com.vibent.vibentback.eventParticipation.EventParticipation;
import com.vibent.vibentback.groupT.GroupT;
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
public class Event implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String ref;

    @NonNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    @PrimaryKeyJoinColumn
    private GroupT group;

    @NonNull
    private String title;

    @NonNull
    private String description;

    @NonNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date endDate;

    @Column(insertable = false, updatable = false)
    @JsonIgnore
    private boolean deleted;

    @JsonProperty
    public String getGroupRef() {
        return group.getRef();
    }

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "event", cascade = CascadeType.ALL)
    private Set<EventParticipation> participations = new HashSet<>();

    @JsonProperty
    public Set<EventParticipationResponse> getParticipationRefs() {
        Set<EventParticipationResponse> participationResponses = new HashSet<>();
        participations.forEach(e -> participationResponses.add(new EventParticipationResponse(e.getUserRef(), e.getAnswer())));
        return participationResponses;
    }

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
}