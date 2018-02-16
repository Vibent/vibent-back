package com.vibent.vibentback.bubble.location;

import com.vibent.vibentback.bubble.alimentation.entry.AlimentationEntry;
import com.vibent.vibentback.user.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/bubble/location")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class LocationBubbleController {

    @Autowired
    LocationBubbleService service;

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
        // TODO
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/{id}")
    LocationBubble updateBubble(@PathVariable Long id, @RequestBody LocationBubble locationBubble) {
        log.info("Update location bubble with id {} and body : {}", id, locationBubble.toString());
        return service.updateBubble(id,locationBubble);
    }

}
