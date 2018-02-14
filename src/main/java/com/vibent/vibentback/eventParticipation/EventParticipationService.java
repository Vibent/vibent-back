package com.vibent.vibentback.eventParticipation;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EventParticipationService {

    EventParticipationRepository eventParticipationRepository;

    public EventParticipation getEventParticipation(long id){
        return eventParticipationRepository.findById(id);
    }
}
