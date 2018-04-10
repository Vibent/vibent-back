package com.vibent.vibentback.bubble.survey;

import com.vibent.vibentback.api.survey.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    SurveyBubble getBubble(@PathVariable Long id) {
        log.info("Get survey bubble with id : {}", id);
        return service.getBubble(id);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    SurveyBubble createBubble(@Valid @RequestBody SurveyBubbleRequest request) {
        log.info("Creating survey bubble for event with ref {}", request.getEventRef());
        return service.createBubble(request.getEventRef());
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/{id}")
    SurveyBubble updateBubble(@PathVariable Long id, @Valid @RequestBody SurveyBubbleUpdateRequest request) {
        log.info("Updating survey bubble with id {} and body : {}", id, request.toString());
        return service.updateBubble(id, request);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    void deleteBubble(@PathVariable Long id) {
        log.info("Deleting survey bubble with id : {}", id);
        service.deleteBubble(id);
    }

    // Survey Bubble Option -------------------------------------------------------------

    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, value = "/option",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    SurveyBubble createOption(@Valid @RequestBody SurveyOptionRequest request) {
        log.info("Creating survey option with body : {}", request.toString());
        return service.createOption(request);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/option/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    SurveyBubble updateOption(@PathVariable Long id, @Valid @RequestBody SurveyOptionUpdateRequest option) {
        log.info("Updating survey option with id {} and body : {}", id, option.toString());
        return service.updateOption(id, option);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "/option/{id}")
    void deleteOption(@PathVariable Long id) {
        log.info("Deleting survey option for with id {}", id);
        service.deleteOption(id);
    }

    // Survey Bubble Answers -------------------------------------------------------------

    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, value = "/answer",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    SurveyBubble createAnswer(@Valid @RequestBody SurveyAnswerRequest request) {
        log.info("Creating survey answer with body : {}", request.toString());
        return service.createAnswer(request);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "/answer/{id}")
    void deleteAnswer(@PathVariable Long id) {
        log.info("Deleting survey answer with id {}", id);
        service.deleteAnswer(id);
    }
}
