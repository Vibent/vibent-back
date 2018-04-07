package com.vibent.vibentback.bubble.free;

import com.vibent.vibentback.api.free.FreeBubbleRequest;
import com.vibent.vibentback.api.free.FreeBubbleUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping(value = "/bubble/free",
        produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class FreeController {

    FreeService service;

// Free Bubble -------------------------------------------------------------

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    FreeBubble getBubble(@PathVariable Long id) {
        log.info("Get free bubble with id : {}", id);
        return service.getBubble(id);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    FreeBubble createBubble(@RequestBody FreeBubbleRequest request) {
        log.info("Creating free bubble for event with ref {}", request.getEventRef());
        return service.createBubble(request.getEventRef());
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/{id}")
    FreeBubble updateBubble(@PathVariable Long id, @RequestBody FreeBubbleUpdateRequest request) {
        log.info("Updating free bubble with id {} and body : {}", id, request.toString());
        return service.updateBubble(id, request);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    void deleteBubble(@PathVariable Long id) {
        log.info("Deleting free bubble with id : {}", id);
        service.deleteBubble(id);
    }


}
