package com.vibent.vibentback.bubble.travel.request;

import org.springframework.data.repository.CrudRepository;



public interface TravelRequestRepository extends CrudRepository<TravelRequest, Long> {

    TravelRequest findById(long id);
    Iterable<TravelRequest> findByBubbleId(long id);
    Iterable<TravelRequest> findByCreatorRef(String ref);

}
