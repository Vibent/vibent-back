package com.vibent.vibentback.event.participation;

import com.vibent.vibentback.common.error.VibentError;
import com.vibent.vibentback.common.error.VibentException;
import com.vibent.vibentback.event.Event;
import com.vibent.vibentback.event.EventRepository;
import com.vibent.vibentback.event.api.UpdateEventParticipationRequest;
import com.vibent.vibentback.user.User;
import com.vibent.vibentback.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;
import java.util.Set;


@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EventParticipationService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final EventParticipationRepository eventParticipationRepository;

    public void createEventParticipations(Event event, Collection<User> users) {
        for (User user : users) {
            this.createEventParticipation(event, user);
        }
    }

    public void createEventParticipations(Collection<Event> events, User user) {
        for (Event event : events) {
            this.createEventParticipation(event, user);
        }
    }

    public void createEventParticipations(Collection<Event> events, Collection<User> users) {
        for (Event event : events) {
            for (User user : users) {
                this.createEventParticipation(event, user);
            }
        }
    }

    public void createEventParticipation(Event event, User user) {
        EventParticipation participation = new EventParticipation();
        participation.setUser(user);
        participation.setEvent(event);
        participation.setVisible(true);
        participation.setAnswer(EventParticipation.Answer.UNANSWERED);
        user.getParticipations().add(participation);
        event.getParticipations().add(participation);
        eventParticipationRepository.save(participation);
    }

    Set<EventParticipation> getEventParticipations(String eventRef) {
        Event event = eventRepository.findByRef(eventRef)
                .orElseThrow(() -> new VibentException(VibentError.EVENT_NOT_FOUND));
        return eventParticipationRepository.findByEvent(event);
    }

    Set<EventParticipation> getUsersEventParticipations(@PathVariable String userRef) {
        User user = userRepository.findByRef(userRef)
                .orElseThrow(() -> new VibentException(VibentError.USER_NOT_FOUND));
        return eventParticipationRepository.findByUser(user);
    }

    EventParticipation updateEventParticipation(Long participationId, UpdateEventParticipationRequest request) {
        EventParticipation participation = eventParticipationRepository.findById(participationId)
                .orElseThrow(() -> new VibentException(VibentError.EVENT_PARTICIPATION_NOT_FOUND));
        if (request.getAnswer() != null)
            participation.setAnswer(request.getAnswer());
        if (request.getIsVisible() != null)
            participation.setVisible(request.getIsVisible());
        return eventParticipationRepository.save(participation);
    }

    public EventParticipation updateAnswer(User user, Event event, EventParticipation.Answer answer) {
        EventParticipation participation = eventParticipationRepository.findByUserAndEvent(user, event)
                .orElseThrow(() -> new VibentException(VibentError.EVENT_PARTICIPATION_NOT_FOUND));
        participation.setAnswer(answer);
        return eventParticipationRepository.save(participation);
    }
}