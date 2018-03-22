package com.vibent.vibentback.bubble.planning;

import com.vibent.vibentback.api.bubble.survey.*;
import com.vibent.vibentback.api.planning.PlanningBubbleRequest;
import com.vibent.vibentback.api.planning.PlanningBubbleUpdateRequest;
import com.vibent.vibentback.api.planning.PlanningEntryRequest;
import com.vibent.vibentback.api.planning.PlanningEntryUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/bubble/planning",
        produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PlanningController {

    PlanningService service;

    // Planning Bubble -------------------------------------------------------------

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    PlanningBubble getBubble(@PathVariable Long id) {
        log.info("Get planning bubble with id : {}", id);
        return service.getBubble(id);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    PlanningBubble createBubble(@RequestBody PlanningBubbleRequest request) {
        log.info("Creating planning bubble for event with ref {}", request.getEventRef());
        return service.createBubble(request.getEventRef());
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/{id}")
    PlanningBubble updateBubble(@PathVariable Long id, @RequestBody PlanningBubbleUpdateRequest request) {
        log.info("Updating planning bubble with id {} and body : {}", id, request.toString());
        return service.updateBubble(id, request);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    void deleteBubble(@PathVariable Long id) {
        log.info("Deleting planning bubble with id : {}", id);
        service.deleteBubble(id);
    }

    // Planning Bubble Entry -------------------------------------------------------------

    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, value = "/entry",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    PlanningBubble createEntry(@RequestBody PlanningEntryRequest entry) {
        log.info("Creating planning entry with body : {}", entry.toString());
        return service.createEntry(entry);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/entry/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    PlanningBubble updateEntry(@PathVariable Long id, @RequestBody PlanningEntryUpdateRequest entry) {
        log.info("Updating planning entry with id {} and body : {}", id, entry.toString());
        return service.updateEntry(id, entry);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "/entry/{id}")
    void deleteEntry(@PathVariable Long id) {
        log.info("Deleting planning entry for with id {}", id);
        service.deleteEntry(id);
    }
}
