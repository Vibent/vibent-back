package com.vibent.vibentback.bubble.ownership;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;


public interface BubbleOwnershipRepository extends CrudRepository<BubbleOwnership, Long> {

    BubbleOwnership findById(long id);

    ArrayList<BubbleOwnership> findByEventRef(String ref);

}
