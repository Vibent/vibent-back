package com.gitlab.vibent.vibentback.eventParticipation;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/eventparticipation")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EventParticipationController {

    private final EventParticipationService eventParticipationService;

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    EventParticipation getEventParticipation(@PathVariable long id) {
        log.info("Get group with id : {}", id);
        return eventParticipationService.getEventParticipation(id);
    }
}
