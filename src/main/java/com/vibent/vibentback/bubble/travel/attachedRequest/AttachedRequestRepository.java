package com.vibent.vibentback.bubble.travel.attachedRequest;

import org.springframework.data.repository.CrudRepository;



public interface AttachedRequestRepository extends CrudRepository<AttachedRequest, Long> {

    AttachedRequest findById(long id);
    Iterable<AttachedRequest> findByProposalId(long id);
    Iterable<AttachedRequest> findByRequestId(long id);

}
