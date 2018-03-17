package com.vibent.vibentback.bubble.survey;

import com.vibent.vibentback.api.bubble.survey.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
    SurveyBubble createBubble(@RequestBody SurveyBubbleRequest request) {
        log.info("Creating survey bubble for event with ref {}", request.getEventRef());
        return service.createBubble(request.getEventRef());
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/{id}")
    SurveyBubble updateBubble(@PathVariable Long id, @RequestBody SurveyBubbleUpdateRequest request) {
        log.info("Updating survey bubble with id {} and body : {}", id, request.toString());
        return service.updateBubble(id, request);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    void deleteBubble(@PathVariable Long id) {
        log.info("Deleting survey bubble with id : {}", id);
        service.deleteBubble(id);
    }

    // Survey Bubble Answer -------------------------------------------------------------

    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, value = "/answer",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    SurveyBubble createAnswer(@RequestBody SurveyAnswerRequest answer) {
        log.info("Creating survey answer with body : {}", answer.toString());
        return service.createAnswer(answer);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/answer/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    SurveyBubble updateAnswer(@PathVariable Long id, @RequestBody SurveyAnswerUpdateRequest answer) {
        log.info("Updating survey answer with id {} and body : {}", id, answer.toString());
        return service.updateAnswer(id, answer);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "/answer/{id}")
    void deleteAnswer(@PathVariable Long id) {
        log.info("Deleting survey answer for with id {}", id);
        service.deleteAnswer(id);
    }

    // Survey Bubble User answer -------------------------------------------------------------

    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, value = "/useranswer",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    SurveyBubble createUserAnswer(@RequestBody UsersSurveyAnswersRequest userAnswer) {
        log.info("Creating survey user answer with body : {}", userAnswer.toString());
        return service.createUserAnswer(userAnswer);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "/useranswer/{id}")
    void deleteBubbleUserAnswer(@PathVariable Long id) {
        log.info("Deleting survey user answer with id {}", id);
        service.deleteUserAnswer(id);
    }
}
