package com.vibent.vibentback.bubble.travel.attachedRequest;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface AttachedRequestRepository extends CrudRepository<AttachedRequest, Long> {

    AttachedRequest findById(long id);
    ArrayList<AttachedRequest> findByProposalId(long id);
    ArrayList<AttachedRequest> findByRequestId(long id);

}
