package com.vibent.vibentback.event.participation;

import com.vibent.vibentback.event.Event;
import com.vibent.vibentback.user.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Set;


public interface EventParticipationRepository extends CrudRepository<EventParticipation, Long> {

    EventParticipation findById(long id);

    Set<EventParticipation> findByUser(User user);

    Set<EventParticipation> findByEvent(Event event);

    @Modifying
    @Transactional
    @Query(value = "UPDATE event_participation SET deleted = FALSE WHERE ref = :ref", nativeQuery = true)
    int recover(@Param("ref") String ref);

    @Query(value = "SELECT deleted FROM event_participation WHERE ref = :ref", nativeQuery = true)
    boolean isDeleted(@Param("ref") String ref);
}
