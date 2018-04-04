package com.vibent.vibentback.bubble.location;

import com.vibent.vibentback.api.location.LocationBubbleUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping(value = "/bubble/location",
        produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class LocationController {

    LocationService service;

    // Location Bubble
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    LocationBubble getBubble(@PathVariable Long id) {
        log.info("Get location bubble with id : {}", id);
        return service.getBubble(id);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    LocationBubble createBubble(@RequestBody String eventRef) {
        log.info("Creating location bubble");
        return service.createBubble(eventRef);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    void deleteBubble(@PathVariable Long id) {
        log.info("Deleting location bubble with id : {}", id);
        service.deleteBubble(id);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/{id}")
    LocationBubble updateBubble(@PathVariable Long id, @RequestBody LocationBubbleUpdateRequest locationBubble) {
        log.info("Update location bubble with id {} and body : {}", id, locationBubble.toString());
        return service.updateBubble(id,locationBubble);
    }

}
