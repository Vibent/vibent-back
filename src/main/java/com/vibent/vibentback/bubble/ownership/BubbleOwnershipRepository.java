package com.vibent.vibentback.bubble.ownership;

import org.springframework.data.repository.CrudRepository;

public interface BubbleOwnershipRepository extends CrudRepository<BubbleOwnership, Long> {

    BubbleOwnership findById(long id);

    Iterable<BubbleOwnership> findByEventRef(String ref);

}
