package com.vibent.vibentback.bubble.ownership;

import com.vibent.vibentback.bubble.BubbleType;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BubbleOwnershipRepository extends CrudRepository<BubbleOwnership, Long> {

    Optional<BubbleOwnership> findByIdAndBubbleType(long id, BubbleType type);

    Iterable<BubbleOwnership> findByEventRef(String ref);

}
