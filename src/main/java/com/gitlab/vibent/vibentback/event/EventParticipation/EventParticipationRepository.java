package com.gitlab.vibent.vibentback.event.EventParticipation;

import org.springframework.data.repository.CrudRepository;
import java.util.ArrayList;

public interface EventParticipationRepository extends CrudRepository<EventParticipation, Long> {

    ArrayList<EventParticipation> findByUserRef(String ref);

    ArrayList<EventParticipation> findByGroupRef(String groupRef);

}
