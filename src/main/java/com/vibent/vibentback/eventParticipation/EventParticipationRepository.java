package com.vibent.vibentback.eventParticipation;

import org.springframework.data.repository.CrudRepository;


public interface EventParticipationRepository extends CrudRepository<EventParticipation, Long> {

    EventParticipation findById(long id);

    Iterable<EventParticipation> findByUserRef(String ref);

    Iterable<EventParticipation> findByGroupRef(String groupRef);

}
