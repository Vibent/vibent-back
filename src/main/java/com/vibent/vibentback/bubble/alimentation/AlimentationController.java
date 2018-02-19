package com.vibent.vibentback.bubble.alimentation;

import com.vibent.vibentback.api.bubble.alimentation.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/bubble/alimentation")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AlimentationController {

    AlimentationService service;

    // Alimentation Bubble -------------------------------------------------------------
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    AlimentationBubbleRes getBubble(@PathVariable Long id) {
        log.info("Get alimentation bubble with id : {}", id);
        return service.getBubble(id);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    AlimentationBubbleRes createBubble(@RequestBody AlimentationBubbleReq request) {
        log.info("Creating alimentation bubble");
        return service.createBubble(request);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    void deleteBubble(@PathVariable Long id) {
        log.info("Deleting alimentation bubble with id : {}", id);
        service.deleteBubble(id);
    }

    // Alimentation Bubble Entry -------------------------------------------------------------

    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, value = "/entry")
    AlimentationBubbleRes createBubbleEntry(@RequestBody AlimentationEntryReq entry) {
        log.info("Creating alimentation entry with body : {}", entry.toString());
        return service.createBubbleEntry(entry);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/entry/{id}")
    AlimentationBubbleRes updateBubbleEntry(@PathVariable Long id, @RequestBody AlimentationEntryUpdateReq entry) {
        log.info("Update alimentation entry with id {} and body : {}", id, entry.toString());
        return service.updateBubbleEntry(id, entry);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "/entry/{id}")
    void deleteBubbleEntry(@PathVariable Long id) {
        log.info("Delete alimentation entry for with id {}", id);
        service.deleteBubbleEntry(id);
    }

    // Alimentation Bubble Entry Bring -------------------------------------------------------------

    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, value = "/bring")
    AlimentationBubbleRes createBubbleBring(@RequestBody AlimentationBringReq bring) {
        log.info("Creating alimentation bring with body : {}", bring.toString());
        return service.createBubbleBring(bring);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/bring/{id}")
    AlimentationBubbleRes updateBubbleBring(@PathVariable Long id, @Valid @RequestBody AlimentationBringUpdateReq bring) {
        log.info("Update alimentation bring with id {} with body : {}", id, bring.toString());
        return service.updateBubbleBring(id, bring);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "/bring/{id}")
    void deleteBubbleBring(@PathVariable Long id) {
        log.info("Delete alimentation bring with id {}", id);
        service.deleteBubbleBring(id);
    }
}
