package com.vibent.vibentback.bubble.planning;

import com.vibent.vibentback.bubble.planning.api.PlanningBubbleRequest;
import com.vibent.vibentback.bubble.planning.api.PlanningBubbleUpdateRequest;
import com.vibent.vibentback.bubble.planning.api.PlanningEntryRequest;
import com.vibent.vibentback.bubble.planning.api.PlanningEntryUpdateRequest;
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
@RequestMapping(value = "/bubble/planning",
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PlanningController {

    private final PlanningService service;

    // Planning Bubble -------------------------------------------------------------
    @PreAuthorize("hasPermission(#id, 'PlanningBubble', 'read')")
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    PlanningBubble getBubble(@PathVariable Long id) {
        log.info("Get planning bubble with id : {}", id);
        return service.getBubble(id);
    }

    @PreAuthorize("hasPermission(#request.eventRef, 'Event', 'writeChildren')")
    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    PlanningBubble createBubble(@Valid @RequestBody PlanningBubbleRequest request) {
        log.info("Creating planning bubble for event with ref {}", request.getEventRef());
        return service.createBubble(request);
    }

    @PreAuthorize("hasPermission(#id, 'PlanningBubble', 'write')")
    @RequestMapping(method = RequestMethod.PATCH, value = "/{id}")
    PlanningBubble updateBubble(@PathVariable Long id, @Valid @RequestBody PlanningBubbleUpdateRequest request) {
        log.info("Updating planning bubble with id {} and body : {}", id, request.toString());
        return service.updateBubble(id, request);
    }

    @PreAuthorize("hasPermission(#id, 'PlanningBubble', 'write')")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    void deleteBubble(@PathVariable Long id) {
        log.info("Deleting planning bubble with id : {}", id);
        service.deleteBubble(id);
    }

    // Planning Bubble Entry -------------------------------------------------------------
    @PreAuthorize("hasPermission(#request.bubbleId, 'PlanningBubble', 'writeChildren')")
    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, value = "/entry",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    PlanningBubble createEntry(@Valid @RequestBody PlanningEntryRequest request) {
        log.info("Creating planning entry with body : {}", request.toString());
        return service.createEntry(request);
    }

    @PreAuthorize("hasPermission(#id, 'PlanningEntry', 'write')")
    @RequestMapping(method = RequestMethod.PATCH, value = "/entry/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    PlanningBubble updateEntry(@PathVariable Long id, @Valid @RequestBody PlanningEntryUpdateRequest entry) {
        log.info("Updating planning entry with id {} and body : {}", id, entry.toString());
        return service.updateEntry(id, entry);
    }

    @PreAuthorize("hasPermission(#id, 'PlanningEntry', 'write')")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "/entry/{id}")
    void deleteEntry(@PathVariable Long id) {
        log.info("Deleting planning entry with id {}", id);
        service.deleteEntry(id);
    }
}
