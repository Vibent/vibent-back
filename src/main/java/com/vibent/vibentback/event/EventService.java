package com.vibent.vibentback.event;

import com.vibent.vibentback.common.ObjectUpdater;
import com.vibent.vibentback.user.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EventService {

    EventRepository eventRepository;

    public Event getEvent(String ref) {
        return eventRepository.findByRef(ref);
    }

    public Event getAllEvent(String ref) {
        // return all event data
        return new Event();
    }

    public Event addEvent(Event event) {
        return eventRepository.save(event);
    }

    public void deleteEvent(String eventRef) {
        Event event = eventRepository.findByRef(eventRef);
        event.setDeleted(true);
        eventRepository.save(event);
    }

    public Event updateEvent(String eventRef, Event newEvent) {
        Event existing = eventRepository.findByRef(eventRef);
        ObjectUpdater.updateProperties(existing, newEvent);
        return eventRepository.save(existing);
    }

}
