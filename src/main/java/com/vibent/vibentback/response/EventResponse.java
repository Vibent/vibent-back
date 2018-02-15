package com.vibent.vibentback.response;

import com.vibent.vibentback.bubble.free.FreeBubble;
import com.vibent.vibentback.bubble.location.LocationBubble;
import com.vibent.vibentback.bubble.ownership.BubbleOwnership;
import com.vibent.vibentback.event.Event;
import com.vibent.vibentback.eventParticipation.EventParticipation;
import lombok.Data;

import java.util.ArrayList;

@Data
public class EventResponse {

    /** Event **/
    Event event;
    /** Event participations **/
    ArrayList<EventParticipation> eventParticipations;
    /** Travel bubbles **/
    ArrayList<TravelBubbleResponse> travelBubbleResponses;
    /** Alimentation bubbles **/
    ArrayList<AlimentationBubbleResponse> alimentationBubbleResponses;
    /** Checkbox bubbles **/
    ArrayList<CheckboxBubbleResponse> checkboxBubbleResponses;
    /** Free bubbles **/
    ArrayList<FreeBubble> freeBubblesResponses;
    /** Location bubbles **/
    ArrayList<LocationBubble> locationBubblesResponses;
    /** Bubbles owners **/
    ArrayList<BubbleOwnership> bubbleOwnershipsResponses;
    /** Planning bubbles **/
    ArrayList<PlanningBubbleResponse> planningBubbleResponses;
    /** Survey bubbles **/
    ArrayList<SurveyBubbleResponse> surveyBubbleResponses;

    //TODO
    /** Comments section **/
}
