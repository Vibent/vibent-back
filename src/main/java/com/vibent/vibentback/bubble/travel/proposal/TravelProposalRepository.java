package com.vibent.vibentback.bubble.travel.proposal;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;


public interface TravelProposalRepository extends CrudRepository<TravelProposal, Long> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE travel_proposal SET deleted = FALSE WHERE id = :id", nativeQuery = true)
    int recover(@Param("id") Long id);

    @Query(value = "SELECT deleted FROM travel_proposal WHERE id = :id", nativeQuery = true)
    boolean isDeleted(@Param("id") Long id);

}
