package com.vibent.vibentback.bubble.survey;

import com.vibent.vibentback.api.bubble.survey.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(value = "/bubble/survey",
        produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SurveyController {

    SurveyService service;

    // Survey Bubble -------------------------------------------------------------
    @PostAuthorize("hasPermission(returnObject, 'read')")
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    SurveyBubble getBubble(@PathVariable Long id) {
        log.info("Get survey bubble with id : {}", id);
        return service.getBubble(id);
    }

    @PreAuthorize("hasPermission(#request.eventRef, 'Event', 'writeChildren')")
    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    SurveyBubble createBubble(@Valid @RequestBody SurveyBubbleRequest request) {
        log.info("Creating survey bubble for event with ref {}", request.getEventRef());
        return service.createBubble(request);
    }

    @PreAuthorize(value = "hasPermission(#id, 'SurveyBubble', 'write')")
    @RequestMapping(method = RequestMethod.PATCH, value = "/{id}")
    SurveyBubble updateBubble(@PathVariable Long id, @Valid @RequestBody SurveyBubbleUpdateRequest request) {
        log.info("Updating survey bubble with id {} and body : {}", id, request.toString());
        return service.updateBubble(id, request);
    }

    @PreAuthorize(value = "hasPermission(#id, 'SurveyBubble', 'write')")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    void deleteBubble(@PathVariable Long id) {
        log.info("Deleting survey bubble with id : {}", id);
        service.deleteBubble(id);
    }

    // Survey Bubble Option -------------------------------------------------------------
    @PreAuthorize(value = "hasPermission(#request.bubbleId, 'SurveyBubble', 'writeChildren')")
    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, value = "/option",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    SurveyBubble createOption(@Valid @RequestBody SurveyOptionRequest request) {
        log.info("Creating survey option with body : {}", request.toString());
        return service.createOption(request);
    }

    @PreAuthorize(value = "hasPermission(#id, 'SurveyOption', 'write')")
    @RequestMapping(method = RequestMethod.PATCH, value = "/option/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    SurveyBubble updateOption(@PathVariable Long id, @Valid @RequestBody SurveyOptionUpdateRequest request) {
        log.info("Updating survey option with id {} and body : {}", id, request.toString());
        return service.updateOption(id, request);
    }

    @PreAuthorize(value = "hasPermission(#id, 'SurveyOption', 'write')")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "/option/{id}")
    void deleteOption(@PathVariable Long id) {
        log.info("Deleting survey option for with id {}", id);
        service.deleteOption(id);
    }

    // Survey Bubble Answer -------------------------------------------------------------
    @PreAuthorize(value = "hasPermission(#request.optionId, 'SurveyOption', 'writeChildren')")
    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, value = "/answer",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    SurveyBubble createAnswer(@Valid @RequestBody SurveyAnswerRequest request) {
        log.info("Creating survey answer with body : {}", request.toString());
        return service.createAnswer(request);
    }

    @PreAuthorize(value = "hasPermission(#id, 'SurveyAnswer', 'write')")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "/answer/{id}")
    void deleteAnswer(@PathVariable Long id) {
        log.info("Deleting survey answer with id {}", id);
        service.deleteAnswer(id);
    }

    @PreAuthorize(value = "hasPermission(#id, 'SurveyAnswer', 'write')")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "/option/{id}/answer")
    void deleteAnswerOfOption(@PathVariable Long id) {
        log.info("Deleting survey answer with option id {}", id);
        service.deleteAnswerOfOption(id);
    }
}
