package com.vibent.vibentback.event;

import com.vibent.vibentback.api.event.ConnectedUserEventsResponse;
import com.vibent.vibentback.api.event.EventRequest;
import com.vibent.vibentback.api.event.EventUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/event")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EventController {

    private final EventService eventService;

    @RequestMapping(method = RequestMethod.GET, value = "/me")
    Set<Event> getConnectedUserEvents() {
        log.info("Getting the connected user's events");
        return eventService.getConnectedUserEvents();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{eventRef}")
    Event getEvent(@PathVariable String eventRef) {
        log.info("Get event with ref : {}", eventRef);
        return eventService.getEvent(eventRef);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    Event createEvent(@Valid @RequestBody EventRequest request) {
        log.info("Creating event with body : {}", request.toString());
        return eventService.createEvent(request);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/{eventRef}")
    Event updateEvent(@PathVariable String eventRef, @Valid @RequestBody EventUpdateRequest request) {
        log.info("Update event with ref {} body : {}", eventRef, request.toString());
        return eventService.updateEvent(eventRef, request);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "/{eventRef}")
    void deleteEvent(@PathVariable String eventRef) {
        log.info("Deleting event with ref : {}", eventRef);
        eventService.deleteEvent(eventRef);
    }
}
