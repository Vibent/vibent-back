package com.vibent.vibentback.bubble.location;

import com.vibent.vibentback.bubble.location.api.LocationBubbleRequest;
import com.vibent.vibentback.bubble.location.api.LocationBubbleUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Slf4j
@RestController
@RequestMapping(value = "/bubble/location",
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LocationController {

    private final LocationService service;

    // Location Bubble
    @PreAuthorize("hasPermission(#id, 'LocationBubble', 'read')")
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    LocationBubble getBubble(@PathVariable Long id) {
        log.info("Get location bubble with id : {}", id);
        return service.getBubble(id);
    }

    @PreAuthorize("hasPermission(#request.eventRef, 'Event', 'writeChildren')")
    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    LocationBubble createBubble(@Valid @RequestBody LocationBubbleRequest request) {
        log.info("Creating free bubble for event with ref {}", request.getEventRef());
        return service.createBubble(request.getEventRef());
    }

    @PreAuthorize("hasPermission(#id, 'LocationBubble', 'write')")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    void deleteBubble(@PathVariable Long id) {
        log.info("Deleting location bubble with id : {}", id);
        service.deleteBubble(id);
    }

    @PreAuthorize("hasPermission(#id, 'LocationBubble', 'write')")
    @RequestMapping(method = RequestMethod.PATCH, value = "/{id}")
    LocationBubble updateBubble(@PathVariable Long id, @Valid @RequestBody LocationBubbleUpdateRequest locationBubble) {
        log.info("Update location bubble with id {} and body : {}", id, locationBubble.toString());
        return service.updateBubble(id, locationBubble);
    }

}
