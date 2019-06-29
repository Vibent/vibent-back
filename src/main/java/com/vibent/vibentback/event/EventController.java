package com.vibent.vibentback.event;

import com.vibent.vibentback.common.api.InviteTokenResponse;
import com.vibent.vibentback.common.api.MailInviteRequest;
import com.vibent.vibentback.event.api.DetailledEventResponse;
import com.vibent.vibentback.event.api.EventRequest;
import com.vibent.vibentback.event.api.EventUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/event")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EventController {

    private final EventService eventService;

    @RequestMapping(method = RequestMethod.GET, value = "/me")
    Set<DetailledEventResponse> getConnectedUserEvents() {
        log.info("Getting the connected user's events");
        Set<DetailledEventResponse> response = new HashSet<>();
        eventService.getConnectedUserEvents().forEach(e -> response.add(new DetailledEventResponse(e)));
        return response;
    }

    @PreAuthorize("hasPermission(#eventRef, 'Event', 'read')")
    @RequestMapping(method = RequestMethod.GET, value = "/{eventRef}")
    DetailledEventResponse getEvent(@PathVariable String eventRef) {
        log.info("Get event with ref : {}", eventRef);
        return new DetailledEventResponse(eventService.getEvent(eventRef));
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    DetailledEventResponse createEvent(@Valid @RequestBody EventRequest request) {
        log.info("Creating event with body : {}", request.toString());
        return new DetailledEventResponse(eventService.createEvent(request));
    }

    @PreAuthorize(value = "hasPermission(#eventRef, 'Event', 'write')")
    @RequestMapping(method = RequestMethod.PATCH, value = "/{eventRef}")
    DetailledEventResponse updateEvent(@PathVariable String eventRef, @Valid @RequestBody EventUpdateRequest request) {
        log.info("Update event with ref {} body : {}", eventRef, request.toString());
        return new DetailledEventResponse(eventService.updateEvent(eventRef, request));
    }

    @PreAuthorize(value = "hasPermission(#eventRef, 'Event', 'write')")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "/{eventRef}")
    void deleteEvent(@PathVariable String eventRef) {
        log.info("Deleting event with ref : {}", eventRef);
        eventService.deleteEvent(eventRef);
    }

    @PreAuthorize(value = "hasPermission(#eventRef, 'Event', 'write')")
    @RequestMapping(method = RequestMethod.GET, value = "/{eventRef}/inviteToken")
    InviteTokenResponse getEventInviteToken(@PathVariable String eventRef) {
        log.info("Getting invite token for event : {}", eventRef);
        return new InviteTokenResponse(eventService.generateEventInviteToken(eventRef));
    }

    @PreAuthorize(value = "hasPermission(#request.ref, 'Event', 'write')")
    @RequestMapping(method = RequestMethod.POST, value = "/mailInvite")
    void mailInvite(@Valid @RequestBody MailInviteRequest request) {
        log.info("Sending mail invite for event : {}", request.getRef());
        eventService.sendMailInvite(request);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/validateInviteToken/{token:.+}")
    DetailledEventResponse validateEventInviteToken(@PathVariable String token) {
        log.info("Validating event invite token : {}", token);
        return new DetailledEventResponse(eventService.validateEventInviteToken(token));
    }
}
