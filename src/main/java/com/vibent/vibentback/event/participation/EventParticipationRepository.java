package com.vibent.vibentback.event.participation;

import com.vibent.vibentback.event.Event;
import com.vibent.vibentback.user.User;
import org.checkerframework.checker.nullness.Opt;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;


public interface EventParticipationRepository extends CrudRepository<EventParticipation, Long> {

    EventParticipation findById(long id);

    Set<EventParticipation> findByUser(User user);

    Set<EventParticipation> findByEvent(Event event);

    Optional<EventParticipation> findByUserAndEvent(User user, Event event);

    @Modifying
    @Transactional
    @Query(value = "UPDATE event_participation SET deleted = FALSE WHERE id = :id", nativeQuery = true)
    int recover(@Param("id") Long id);

    @Query(value = "SELECT deleted FROM event_participation WHERE id = :id", nativeQuery = true)
    boolean isDeleted(@Param("id") Long id);
}
