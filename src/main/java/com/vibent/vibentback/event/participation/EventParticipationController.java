package com.vibent.vibentback.event.participation;

import com.vibent.vibentback.event.api.UpdateEventParticipationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/participation")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EventParticipationController {

    private final EventParticipationService eventParticipationService;

    /**
     * Get all EventParticipations relating to a specific event
     */
    @RequestMapping(method = RequestMethod.GET, value = "/event/{eventRef}")
    Set<EventParticipation> getEventParticipations(@PathVariable String eventRef) {
        log.info("Get event participations for event : {}", eventRef);
        return eventParticipationService.getEventParticipations(eventRef);
    }

    /**
     * Get all EventParticipations relating to a specific user
     */
    @RequestMapping(method = RequestMethod.GET, value = "/user/{userRef}")
    Set<EventParticipation> getUserParticipations(@PathVariable String userRef) {
        log.info("Get event participations for user : {}", userRef);
        return eventParticipationService.getUsersEventParticipations(userRef);
    }

    /**
     * Update an event participation - this can be to set it to visible or not or to change
     * the option
     */
    @RequestMapping(method = RequestMethod.PATCH, value = "/{participationId}")
    EventParticipation updateEventParticipation(@PathVariable Long participationId, @Valid @RequestBody UpdateEventParticipationRequest request) {
        log.info("Update event participation with id {} and body {}", participationId, request);
        return eventParticipationService.updateEventParticipation(participationId, request);
    }
}
