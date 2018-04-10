package com.vibent.vibentback.bubble.alimentation;

import com.vibent.vibentback.api.alimentation.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(value = "/bubble/alimentation",
        produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AlimentationController {

    AlimentationService service;

    // Alimentation Bubble -------------------------------------------------------------
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    AlimentationBubble getBubble(@PathVariable Long id) {
        log.info("Get alimentation bubble with id : {}", id);
        return service.getBubble(id);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    AlimentationBubble createBubble(@Valid @RequestBody AlimentationBubbleRequest request) {
        log.info("Creating alimentation bubble for event with ref {}", request.getEventRef());
        return service.createBubble(request.getEventRef());
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    void deleteBubble(@PathVariable Long id) {
        log.info("Deleting alimentation bubble with id : {}", id);
        service.deleteBubble(id);
    }

    // Alimentation Entry -------------------------------------------------------------
    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, value = "/entry",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    AlimentationBubble createEntry(@Valid @RequestBody AlimentationEntryRequest request) {
        log.info("Creating alimentation option with body {}", request.toString());
        return service.createEntry(request);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/entry/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    AlimentationBubble updateEntry(@PathVariable("id") Long id, @Valid @RequestBody AlimentationEntryUpdateRequest request) {
        log.info("Updating alimentation option {} with body {}", id, request.toString());
        return service.updateEntry(id, request);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "/entry/{id}")
    void deleteEntry(@PathVariable Long id) {
        log.info("Deleting alimentation option with id : {}", id);
        service.deleteEntry(id);
    }


    // Alimentation Bring -------------------------------------------------------------
    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, value = "/bring",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    AlimentationBubble createBring(@Valid @RequestBody AlimentationBringRequest request) {
        log.info("Creating alimentation option with body {}", request.toString());
        return service.createBring(request);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/bring/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    AlimentationBubble updateBring(@PathVariable("id") Long id, @Valid @RequestBody AlimentationBringUpdateRequest request) {
        log.info("Updating alimentation bring {} with body {}", id, request.toString());
        return service.updateBring(id, request);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "/bring/{id}")
    void deleteBring(@PathVariable Long id) {
        log.info("Deleting alimentation bring with id : {}", id);
        service.deleteBring(id);
    }
}
