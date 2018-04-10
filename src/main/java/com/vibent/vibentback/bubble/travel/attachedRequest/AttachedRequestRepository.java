package com.vibent.vibentback.bubble.travel.attachedRequest;

import org.springframework.data.repository.CrudRepository;

import java.util.Set;


public interface AttachedRequestRepository extends CrudRepository<AttachedRequest, Long> {

    AttachedRequest findById(long id);

    Set<AttachedRequest> findByProposalId(long id);

    Set<AttachedRequest> findByRequestId(long id);

}
