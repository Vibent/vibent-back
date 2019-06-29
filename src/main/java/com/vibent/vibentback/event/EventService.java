package com.vibent.vibentback.event;

import com.vibent.vibentback.common.api.MailInviteRequest;
import com.vibent.vibentback.common.error.VibentError;
import com.vibent.vibentback.common.error.VibentException;
import com.vibent.vibentback.common.mail.MailService;
import com.vibent.vibentback.common.util.TokenInfo;
import com.vibent.vibentback.common.util.TokenUtils;
import com.vibent.vibentback.event.api.EventRequest;
import com.vibent.vibentback.event.api.EventUpdateRequest;
import com.vibent.vibentback.event.participation.EventParticipation;
import com.vibent.vibentback.event.participation.EventParticipationService;
import com.vibent.vibentback.user.ConnectedUserUtils;
import com.vibent.vibentback.user.User;
import com.vibent.vibentback.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EventService {

    private final ConnectedUserUtils connectedUserUtils;
    private final EventRepository eventRepository;
    private final EventParticipationService eventParticipationService;
    private final UserRepository userRepository;
    private final TokenUtils tokenUtils;
    private final MailService mailService;

    public Set<Event> getConnectedUserEvents() {
        return connectedUserUtils.getConnectedUser()
                .getParticipations().stream()
                .map(EventParticipation::getEvent)
                .collect(Collectors.toSet());
    }

    public Event getEvent(String ref) {
        return eventRepository.findByRef(ref)
                .orElseThrow(() -> new VibentException(VibentError.EVENT_NOT_FOUND));
    }

    public void deleteEvent(String eventRef) {
        eventRepository.deleteByRef(eventRef);
    }

    public Event updateEvent(String eventRef, EventUpdateRequest request) {
        Event existing = eventRepository.findByRef(eventRef)
                .orElseThrow(() -> new VibentException(VibentError.EVENT_NOT_FOUND));
        if (request.getTitle() != null) existing.setTitle(request.getTitle());
        if (request.getDescription() != null) existing.setDescription(request.getDescription());
        if (request.getEndDate() != null) existing.setEndDate(request.getEndDate());
        if (request.getStartDate() != null) existing.setStartDate(request.getStartDate());

        // Don't save entity if dates aren't coherent
        if (existing.getStartDate().before(new Date()))
            throw new VibentException(VibentError.EVENT_DATE_INVALID);
        if (existing.getStartDate() != null && existing.getEndDate() != null && existing.getStartDate().after(existing.getEndDate()))
            throw new VibentException(VibentError.EVENT_DATE_INVALID);
        return eventRepository.save(existing);
    }

    public Event createEvent(EventRequest request) {
        if (request.getStartDate().before(new Date()))
            throw new VibentException(VibentError.EVENT_DATE_INVALID);
        if (request.getEndDate() != null && request.getStartDate().after(request.getEndDate()))
            throw new VibentException(VibentError.EVENT_DATE_INVALID);
        Event event = new Event();
        event.setRef(UUID.randomUUID().toString());
        event.setTitle(request.getTitle());
        if (request.getDescription() != null)
            event.setDescription(request.getDescription());
        event.setStartDate(request.getStartDate());
        event.setEndDate(request.getEndDate());
        event.setCreator(connectedUserUtils.getConnectedUser());
        Event created = eventRepository.save(event);

        Set<User> invitedUsers = new HashSet<>();
        invitedUsers.add(connectedUserUtils.getConnectedUser());

        if (request.getInvitedUserRefs() != null) {
            for (String userRef : request.getInvitedUserRefs()) {
                User user = userRepository.findByRef(userRef)
                        .orElseThrow(() -> new VibentException(VibentError.USER_NOT_FOUND));
                invitedUsers.add(user);
            }
        }

        eventParticipationService.createEventParticipations(event, invitedUsers);

        return eventRepository.save(created);
    }

    public String generateEventInviteToken(String eventRef) {
        Event event = eventRepository.findByRef(eventRef)
                .orElseThrow(() -> new VibentException(VibentError.EVENT_NOT_FOUND));

        return tokenUtils.getEventInviteToken(event.getId());

    }

    public Event validateEventInviteToken(String token) {
        TokenInfo info = tokenUtils.readEventInviteToken(token);
        Event event = eventRepository.findById(info.getId())
                .orElseThrow(() -> new VibentException(VibentError.EVENT_NOT_FOUND));
        eventParticipationService.createEventParticipation(event, connectedUserUtils.getConnectedUser());
        return event;
    }

    public void sendMailInvite(MailInviteRequest request) {
        Event event = eventRepository.findByRef(request.getRef())
                .orElseThrow(() -> new VibentException(VibentError.EVENT_NOT_FOUND));
        User inviter = connectedUserUtils.getConnectedUser();
        Set<String> recipients = request.getRecipients();
        mailService.sendEventInviteMail(inviter, event, recipients);
    }
}
