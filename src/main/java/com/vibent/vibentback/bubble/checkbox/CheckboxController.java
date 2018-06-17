package com.vibent.vibentback.bubble.checkbox;

import com.vibent.vibentback.api.bubble.checkbox.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(value = "/bubble/checkbox",
        produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CheckboxController {

    CheckboxService service;

    // Checkbox Bubble -------------------------------------------------------------
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    CheckboxBubble getBubble(@PathVariable Long id) {
        log.info("Get checkbox bubble with id : {}", id);
        return service.getBubble(id);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    CheckboxBubble createBubble(@Valid @RequestBody CheckboxBubbleRequest request) {
        log.info("Creating checkbox bubble for event with ref {}", request.getEventRef());
        return service.createBubble(request.getEventRef());
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/{id}")
    CheckboxBubble updateBubble(@PathVariable Long id, @Valid @RequestBody CheckboxBubbleUpdateRequest request) {
        log.info("Updating checkbox bubble with id {} and body : {}", id, request.toString());
        return service.updateBubble(id, request);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    void deleteBubble(@PathVariable Long id) {
        log.info("Deleting checkbox bubble with id : {}", id);
        service.deleteBubble(id);
    }

    // Checkbox Bubble Option -------------------------------------------------------------

    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, value = "/option",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    CheckboxBubble createOption(@Valid @RequestBody CheckboxOptionRequest request) {
        log.info("Creating checkbox option with body : {}", request.toString());
        return service.createOption(request);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/option/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    CheckboxBubble updateOption(@PathVariable Long id, @Valid @RequestBody CheckboxOptionUpdateRequest request) {
        log.info("Updating checkbox option with id {} and body : {}", id, request.toString());
        return service.updateOption(id, request);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "/option/{id}")
    void deleteOption(@PathVariable Long id) {
        log.info("Deleting checkbox option for with id {}", id);
        service.deleteOption(id);
    }

    // Checkbox Bubble Answer -------------------------------------------------------------

    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, value = "/answer",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    CheckboxBubble createAnswer(@Valid @RequestBody CheckboxAnswerRequest request) {
        log.info("Creating checkbox answer with body : {}", request.toString());
        return service.createAnswer(request);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "/answer/{id}")
    void deleteAnswer(@PathVariable Long id) {
        log.info("Deleting checkbox answer with id {}", id);
        service.deleteAnswer(id);
    }
}
