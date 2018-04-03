package com.vibent.vibentback.bubble.checkbox;

import com.vibent.vibentback.api.checkbox.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
    CheckboxBubble createBubble(@RequestBody CheckboxBubbleRequest request) {
        log.info("Creating checkbox bubble for event with ref {}", request.getEventRef());
        return service.createBubble(request.getEventRef());
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/{id}")
    CheckboxBubble updateBubble(@PathVariable Long id, @RequestBody CheckboxBubbleUpdateRequest request) {
        log.info("Updating checkbox bubble with id {} and body : {}", id, request.toString());
        return service.updateBubble(id, request);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    void deleteBubble(@PathVariable Long id) {
        log.info("Deleting checkbox bubble with id : {}", id);
        service.deleteBubble(id);
    }

    // Checkbox Bubble Response -------------------------------------------------------------

    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, value = "/response",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    CheckboxBubble createResponse(@RequestBody CheckboxResponseRequest responseRequest) {
        log.info("Creating checkbox response with body : {}", responseRequest.toString());
        return service.createResponse(responseRequest);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/response/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    CheckboxBubble updateResponse(@PathVariable Long id, @RequestBody CheckboxResponseUpdateRequest response) {
        log.info("Updating checkbox response with id {} and body : {}", id, response.toString());
        return service.updateResponse(id, response);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "/response/{id}")
    void deleteResponse(@PathVariable Long id) {
        log.info("Deleting checkbox response for with id {}", id);
        service.deleteResponse(id);
    }

    // Checkbox Bubble User Response -------------------------------------------------------------

    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, value = "/userresponse",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    CheckboxBubble createUserAnswer(@RequestBody UsersCheckboxResponsesRequest userResponse) {
        log.info("Creating checkbox user response with body : {}", userResponse.toString());
        return service.createUserResponse(userResponse);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "/userresponse/{id}")
    void deleteBubbleUserResponse(@PathVariable Long id) {
        log.info("Deleting checkbox user response with id {}", id);
        service.deleteUserResponse(id);
    }
}
