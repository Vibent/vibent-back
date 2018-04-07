package com.vibent.vibentback.bubble.travel.proposal;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Set;


public interface TravelProposalRepository extends CrudRepository<TravelProposal, Long> {

    TravelProposal findById(long id);

    Set<TravelProposal> findByBubbleId(long id);

    Set<TravelProposal> findByDriverRef(String ref);

    @Transactional
    int deleteById(long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE travel_proposal SET deleted = FALSE WHERE id = :id", nativeQuery = true)
    int recover(@Param("id") long id);

    @Query(value = "SELECT deleted FROM travel_proposal WHERE id = :id", nativeQuery = true)
    boolean isDeleted(@Param("id") long id);

}
