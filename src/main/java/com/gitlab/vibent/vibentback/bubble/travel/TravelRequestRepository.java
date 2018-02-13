package com.gitlab.vibent.vibentback.bubble.travel;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface TravelRequestRepository extends CrudRepository<TravelRequest, Long> {

    TravelRequest findById(long id);
    ArrayList<TravelRequest> findByBubbleId(long id);
    ArrayList<TravelRequest> findByCreatorRef(String ref);

}
