package com.gitlab.vibent.vibentback.eventParticipation;

import org.springframework.data.repository.CrudRepository;
import java.util.ArrayList;

public interface EventParticipationRepository extends CrudRepository<EventParticipation, Long> {

    EventParticipation findById(long id);

    ArrayList<EventParticipation> findByUserRef(String ref);

    ArrayList<EventParticipation> findByGroupRef(String groupRef);

}
