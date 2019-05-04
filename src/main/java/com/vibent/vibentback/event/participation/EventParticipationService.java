package com.vibent.vibentback.event.participation;

import com.vibent.vibentback.common.error.VibentError;
import com.vibent.vibentback.common.error.VibentException;
import com.vibent.vibentback.event.Event;
import com.vibent.vibentback.event.EventRepository;
import com.vibent.vibentback.event.api.UpdateEventParticipationRequest;
import com.vibent.vibentback.group.GroupT;
import com.vibent.vibentback.group.GroupTRepository;
import com.vibent.vibentback.group.membership.Membership;
import com.vibent.vibentback.user.User;
import com.vibent.vibentback.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EventParticipationService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final GroupTRepository groupTRepository;
    private final EventParticipationRepository eventParticipationRepository;

    /**
     * Creates all default event participations when event is created
     * based on members in the events group
     *
     * @param event the event to add the participations to
     */
    public void createNewlyCreatedEventParticipations(Event event) {
        // If there are already participations, then this would create duplicates
        if (!eventParticipationRepository.findByEvent(event).isEmpty()) {
            throw new VibentException(VibentError.EVENT_PARTICIPATION_ALREADY_EXISTS);
        }

        GroupT groupT = event.getGroup();
        createEventParticipations(event, groupT.getMemberships().stream().map(Membership::getUser).collect(Collectors.toSet()));
    }

    public void createEventParticipations(Event event, Collection<User> users) {
        for (User user : users) {
            this.createEventParticipation(event, user);
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
}