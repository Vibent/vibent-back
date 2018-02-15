package com.vibent.vibentback.bubble.travel.proposal;

import org.springframework.data.repository.CrudRepository;



public interface TravelProposalRepository extends CrudRepository<TravelProposal, Long> {

    TravelProposal findById(long id);
    Iterable<TravelProposal> findByBubbleId(long id);
    Iterable<TravelProposal> findByDriverRef(String ref);

}
