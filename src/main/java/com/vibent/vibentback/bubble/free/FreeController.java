package com.vibent.vibentback.bubble.free;

import com.vibent.vibentback.bubble.free.api.FreeBubbleRequest;
import com.vibent.vibentback.bubble.free.api.FreeBubbleUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Slf4j
@RestController
@RequestMapping(value = "/bubble/free",
        produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class FreeController {

    FreeService service;

    // Free Bubble -------------------------------------------------------------
    @PreAuthorize("hasPermission(#id, 'FreeBubble', 'read')")
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    FreeBubble getBubble(@PathVariable Long id) {
        log.info("Get free bubble with id : {}", id);
        return service.getBubble(id);
    }

    @PreAuthorize("hasPermission(#request.eventRef, 'Event', 'writeChildren')")
    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    FreeBubble createBubble(@Valid @RequestBody FreeBubbleRequest request) {
        log.info("Creating free bubble for event with ref {}", request.getEventRef());
        return service.createBubble(request);
    }

    @PreAuthorize("hasPermission(#id, 'FreeBubble', 'write')")
    @RequestMapping(method = RequestMethod.PATCH, value = "/{id}")
    FreeBubble updateBubble(@PathVariable Long id, @Valid @RequestBody FreeBubbleUpdateRequest request) {
        log.info("Updating free bubble with id {} and body : {}", id, request.toString());
        return service.updateBubble(id, request);
    }

    @PreAuthorize("hasPermission(#id, 'FreeBubble', 'write')")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    void deleteBubble(@PathVariable Long id) {
        log.info("Deleting free bubble with id : {}", id);
        service.deleteBubble(id);
    }


}
