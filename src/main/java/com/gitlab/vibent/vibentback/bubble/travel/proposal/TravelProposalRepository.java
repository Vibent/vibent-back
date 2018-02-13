package com.gitlab.vibent.vibentback.bubble.travel.proposal;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface TravelProposalRepository extends CrudRepository<TravelProposal, Long> {

    TravelProposal findById(long id);
    ArrayList<TravelProposal> findByBubbleId(long id);
    ArrayList<TravelProposal> findByDriverRef(String ref);

}
