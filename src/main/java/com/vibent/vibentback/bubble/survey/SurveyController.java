package com.vibent.vibentback.bubble.survey;

import com.vibent.vibentback.api.bubble.survey.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/bubble/survey")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SurveyController {

    SurveyService service;

    // Survey Bubble -------------------------------------------------------------
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    SurveyBubbleRes getBubble(@PathVariable Long id) {
        log.info("Get survey bubble with id : {}", id);
        return service.getBubble(id);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    SurveyBubbleRes createBubble(@RequestBody SurveyBubbleReq request) {
        log.info("Creating survey bubble");
        return service.createBubble(request);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/{id}")
    SurveyBubbleRes updateBubble(@PathVariable Long id, @RequestBody SurveyBubbleUpdateReq request) {
        log.info("Update survey bubble with id {} and body : {}", id, request.toString());
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
    @RequestMapping(method = RequestMethod.POST, value = "/answer")
    SurveyBubbleRes createBubbleAnswer(@RequestBody SurveyAnswerReq answer) {
        log.info("Creating survey answer with body : {}", answer.toString());
        return service.createBubbleAnswer(answer);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/answer/{id}")
    SurveyBubbleRes updateBubbleAnswer(@PathVariable Long id, @RequestBody SurveyAnswerUpdateReq answer) {
        log.info("Update survey answer with id {} and body : {}", id, answer.toString());
        return service.updateBubbleAnswer(id, answer);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "/answer/{id}")
    void deleteBubbleAnswer(@PathVariable Long id) {
        log.info("Delete survey answer for with id {}", id);
        service.deleteBubbleAnswer(id);
    }

    // Survey Bubble User answer -------------------------------------------------------------

    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, value = "/useranswer")
    SurveyBubbleRes createBubbleUserAnswer(@RequestBody UsersSurveyAnswersReq userAnswer) {
        log.info("Creating survey user answer with body : {}", userAnswer.toString());
        return service.createBubbleUserAnswer(userAnswer);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "/useranswer/{id}")
    void deleteBubbleUserAnswer(@PathVariable Long id) {
        log.info("Delete survey user answer with id {}", id);
        service.deleteBubbleUserAnswer(id);
    }
}
