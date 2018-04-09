package com.vibent.vibentback.bubble.travel.request;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;


public interface TravelRequestRepository extends CrudRepository<TravelRequest, Long> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE travel_request SET deleted = FALSE WHERE id = :id", nativeQuery = true)
    int recover(@Param("id") Long id);

    @Query(value = "SELECT deleted FROM travel_request WHERE id = :id", nativeQuery = true)
    boolean isDeleted(@Param("id") Long id);

}
