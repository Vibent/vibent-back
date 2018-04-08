package com.vibent.vibentback.bubble.travel.request;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Set;


public interface TravelRequestRepository extends CrudRepository<TravelRequest, Long> {

    TravelRequest findById(long id);

    Set<TravelRequest> findByBubbleId(long id);

    Set<TravelRequest> findByCreatorRef(String ref);

    @Transactional
    int deleteById(long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE travel_request SET deleted = FALSE WHERE id = :id", nativeQuery = true)
    int recover(@Param("id") long id);

    @Query(value = "SELECT deleted FROM travel_request WHERE id = :id", nativeQuery = true)
    boolean isDeleted(@Param("id") long id);

}
