package com.vibent.vibentback.bubble.travel;

import com.vibent.vibentback.api.travel.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(value = "/bubble/travel",
        produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TravelController {

    TravelService service;

    // Travel Bubble -------------------------------------------------------------
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    TravelBubble getBubble(@PathVariable Long id) {
        log.info("Get travel bubble with id : {}", id);
        return service.getBubble(id);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    TravelBubble createBubble(@Valid @RequestBody TravelBubbleRequest request) {
        log.info("Creating travel bubble for event with ref {}", request.getEventRef());
        return service.createBubble(request.getEventRef());
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    void deleteBubble(@PathVariable Long id) {
        log.info("Deleting travel bubble with id : {}", id);
        service.deleteBubble(id);
    }

    // Travel Proposal -------------------------------------------------------------
    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, value = "/proposal",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    TravelBubble createProposal(@Valid @RequestBody TravelProposalRequest request) {
        log.info("Creating travel proposal with body {}", request.toString());
        return service.createProposal(request);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/proposal/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    TravelBubble updateProposal(@PathVariable("id") Long id, @Valid @RequestBody TravelProposalUpdateRequest request) {
        log.info("Updating travel proposal {} with body {}", id, request.toString());
        return service.updateProposal(id, request);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "/proposal/{id}")
    void deleteEntry(@PathVariable Long id) {
        log.info("Deleting travel proposal with id : {}", id);
        service.deleteProposal(id);
    }

    // Travel Request -------------------------------------------------------------
    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, value = "/request",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    TravelBubble createRequest(@Valid @RequestBody TravelRequestRequest request) {
        log.info("Creating travel request with body {}", request.toString());
        return service.createRequest(request);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/request/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    TravelBubble updateBring(@PathVariable("id") Long id, @Valid @RequestBody TravelRequestUpdateRequest request) {
        log.info("Updating travel request {} with body {}", id, request.toString());
        return service.updateRequest(id, request);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "/request/{id}")
    void deleteBring(@PathVariable Long id) {
        log.info("Deleting travel request with id : {}", id);
        service.deleteRequest(id);
    }
}
