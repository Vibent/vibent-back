package com.vibent.vibentback.event;

import com.vibent.vibentback.ConnectedUserUtils;
import com.vibent.vibentback.api.event.EventRequest;
import com.vibent.vibentback.api.event.EventUpdateRequest;
import com.vibent.vibentback.error.VibentError;
import com.vibent.vibentback.error.VibentException;
import com.vibent.vibentback.eventParticipation.EventParticipationService;
import com.vibent.vibentback.groupT.GroupT;
import com.vibent.vibentback.groupT.GroupTRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EventService {

    ConnectedUserUtils connectedUserUtils;
    EventRepository eventRepository;
    EventParticipationService eventParticipationService;
    GroupTRepository groupTRepository;

    public Set<Event> getConnectedUserEvents() {
        Set<GroupT> groups = connectedUserUtils.getConnectedUser().getMemberships();
        return groups.stream().flatMap(g -> g.getEvents().stream()).collect(Collectors.toSet());
    }

    public Event getEvent(String ref) {
        return eventRepository.findByRef(ref)
                .orElseThrow(() -> new VibentException(VibentError.EVENT_NOT_FOUND));
    }

    public Event createEvent(EventRequest request) {
        if(request.getStartDate().before(new Date()))
            throw new VibentException(VibentError.EVENT_DATE_INVALID);
        if(request.getEndDate() != null && request.getStartDate().after(request.getEndDate()))
            throw new VibentException(VibentError.EVENT_DATE_INVALID);
        GroupT group = groupTRepository.findByRef(request.getGroupRef())
                .orElseThrow(() -> new VibentException(VibentError.GROUP_NOT_FOUND));
        Event event = new Event();
        event.setRef(UUID.randomUUID().toString());
        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setGroup(group);
        event.setStartDate(request.getStartDate());
        event.setEndDate(request.getEndDate());
        Event created = eventRepository.save(event);
        eventParticipationService.createAllEventParticipations(event);
        return eventRepository.save(created);
    }

    public void deleteEvent(String eventRef) {
        eventRepository.deleteByRef(eventRef);
    }

    public Event updateEvent(String eventRef, EventUpdateRequest request) {
        Event existing = eventRepository.findByRef(eventRef)
                .orElseThrow(() -> new VibentException(VibentError.EVENT_NOT_FOUND));
        if(request.getTitle() != null) existing.setTitle(request.getTitle());
        if(request.getDescription() != null) existing.setDescription(request.getDescription());
        if(request.getEndDate() != null) existing.setEndDate(request.getEndDate());
        if(request.getStartDate() != null) existing.setStartDate(request.getStartDate());

        // Don't save entity if dates aren't coherent
        if(existing.getStartDate().before(new Date()))
            throw new VibentException(VibentError.EVENT_DATE_INVALID);
        if(existing.getStartDate() != null && existing.getEndDate() != null && existing.getStartDate().after(existing.getEndDate()))
            throw new VibentException(VibentError.EVENT_DATE_INVALID);
        return eventRepository.save(existing);
    }
}
