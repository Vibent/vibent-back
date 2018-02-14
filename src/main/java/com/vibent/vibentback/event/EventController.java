package com.vibent.vibentback.event;

import com.vibent.vibentback.user.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/event")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EventController {

    private final EventService eventService;

    @RequestMapping(method = RequestMethod.GET, value = "/{eventRef}")
    Event getEvent(@PathVariable String eventRef) {
        log.info("Get event with ref : {}", eventRef);
        return eventService.getEvent(eventRef);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    Event createEvent(@RequestBody Event event){
        log.info("Creating event with body : {}", event.toString());
        return eventService.addEvent(event);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/{eventRef}")
    Event updateEvent(@PathVariable String eventRef, @RequestBody Event event){
        log.info("Update event with ref {} body : {}", eventRef, event.toString());
        return eventService.updateEvent(eventRef, event);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "/{eventRef}")
    void deleteEvent(@PathVariable String eventRef){
        log.info("Deleting event with ref : {}", eventRef);
        eventService.deleteEvent(eventRef);
    }
}
