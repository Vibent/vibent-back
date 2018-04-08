package com.vibent.vibentback.event;

import com.vibent.vibentback.common.ObjectUpdater;
import com.vibent.vibentback.error.VibentError;
import com.vibent.vibentback.error.VibentException;
import com.vibent.vibentback.eventParticipation.EventParticipation;
import com.vibent.vibentback.eventParticipation.EventParticipationRepository;
import com.vibent.vibentback.eventParticipation.EventParticipationService;
import com.vibent.vibentback.user.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EventService {

    EventRepository eventRepository;

    EventParticipationService eventParticipationService;

    public Event getEvent(String ref) {
        return eventRepository.findByRef(ref)
                .orElseThrow(() -> new VibentException(VibentError.EVENT_NOT_FOUND));
    }

    public Event getAllEvent(String ref) {
        // return all event data
        return new Event();
    }

    public Event addEvent(Event event) {
        event = eventRepository.save(event);
        eventParticipationService.createAllEventParticipations(event);
        return eventRepository.save(event);
    }

    public void deleteEvent(String eventRef) {
        eventRepository.deleteByRef(eventRef);
    }

    public Event updateEvent(String eventRef, Event newEvent) {
        Event existing = eventRepository.findByRef(eventRef)
                .orElseThrow(() -> new VibentException(VibentError.EVENT_NOT_FOUND));
        ObjectUpdater.updateProperties(existing, newEvent);
        return eventRepository.save(existing);
    }

}
