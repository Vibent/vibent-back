package com.vibent.vibentback.eventParticipation;

import com.vibent.vibentback.event.Event;
import com.vibent.vibentback.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;


public interface EventParticipationRepository extends CrudRepository<EventParticipation, Long> {

    EventParticipation findById(long id);

    Set<EventParticipation> findByUser(User user);

    Set<EventParticipation> findByEvent(Event event);
}
