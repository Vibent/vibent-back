package com.vibent.vibentback.bubble.alimentation;

import com.fasterxml.jackson.databind.node.TextNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

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
    AlimentationBubble createBubble(@RequestBody TextNode eventRef) {
        log.info("Creating alimentation bubble for event with ref {}", eventRef.asText());
        return service.createBubble(eventRef.asText());
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    void deleteBubble(@PathVariable Long id) {
        log.info("Deleting alimentation bubble with id : {}", id);
        service.deleteBubble(id);
    }

}
