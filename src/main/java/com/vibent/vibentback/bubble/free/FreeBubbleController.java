package com.vibent.vibentback.bubble.free;

import com.vibent.vibentback.bubble.alimentation.entry.AlimentationEntry;
import com.vibent.vibentback.user.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/bubble/free")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class FreeBubbleController {

    @Autowired
    FreeBubbleService service;

    // Free Bubble
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    FreeBubble getBubble(@PathVariable Long id) {
        log.info("Get free bubble with id : {}", id);
        return service.getBubble(id);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    FreeBubble createBubble(@RequestBody String eventRef) {
        log.info("Creating free bubble");
        return service.createBubble(eventRef);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    void deleteBubble(@PathVariable Long id) {
        log.info("Deleting free bubble with id : {}", id);
        // TODO
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/{id}")
    FreeBubble updateBubble(@PathVariable Long id, @RequestBody FreeBubble freeBubble) {
        log.info("Update free bubble with id {} and body : {}", id, freeBubble.toString());
        return service.updateBubble(id,freeBubble);
    }

}
