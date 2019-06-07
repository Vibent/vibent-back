package com.vibent.vibentback.event.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vibent.vibentback.bubble.alimentation.AlimentationBubble;
import com.vibent.vibentback.bubble.checkbox.CheckboxBubble;
import com.vibent.vibentback.bubble.free.FreeBubble;
import com.vibent.vibentback.bubble.location.LocationBubble;
import com.vibent.vibentback.bubble.planning.PlanningBubble;
import com.vibent.vibentback.bubble.survey.SurveyBubble;
import com.vibent.vibentback.bubble.travel.TravelBubble;
import com.vibent.vibentback.event.Event;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class DetailledEventResponse {

    @JsonIgnore
    private Event event;

    public DetailledEventResponse(Event event) {
        this.event = event;
    }

    @JsonProperty
    public String getRef() {
        return event.getRef();
    }

    @JsonProperty
    public String getGroupRef() {
        if (event.isStandalone()) {
            return null;
        }
        return event.getGroup().getRef();
    }

    @JsonProperty
    public Boolean isStandalone() {
        return event.isStandalone();
    }

    @JsonProperty
    public String getCreatorRef() {
        return event.getCreator().getRef();
    }

    @JsonProperty
    public String getTitle() {
        return event.getTitle();
    }

    @JsonProperty
    public String getDescription() {
        return event.getDescription();
    }

    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    public Date getStartDate() {
        return event.getStartDate();
    }

    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    public Date getEndDate() {
        return event.getEndDate();
    }

    @JsonProperty
    public Set<EventParticipationResponse> getParticipationRefs() {
        Set<EventParticipationResponse> participationResponses = new HashSet<>();
        event.getParticipations().forEach(e -> participationResponses.add(new EventParticipationResponse(e.getUserRef(), e.getAnswer())));
        return participationResponses;
    }

    @JsonProperty
    public Set<AlimentationBubble> getAlimentationBubbles() {
        return event.getAlimentationBubbles();
    }

    @JsonProperty
    public Set<CheckboxBubble> getCheckboxBubbles() {
        return event.getCheckboxBubbles();
    }

    @JsonProperty
    public Set<FreeBubble> getFreeBubbles() {
        return event.getFreeBubbles();
    }

    @JsonProperty
    public Set<LocationBubble> getLocationBubbles() {
        return event.getLocationBubbles();
    }

    @JsonProperty
    public Set<PlanningBubble> getPlanningBubbles() {
        return event.getPlanningBubbles();
    }

    @JsonProperty
    public Set<SurveyBubble> getSurveyBubbles() {
        return event.getSurveyBubbles();
    }

    @JsonProperty
    public Set<TravelBubble> getTravelBubbles() {
        return event.getTravelBubbles();
    }
}
