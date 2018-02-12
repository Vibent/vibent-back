package com.gitlab.vibent.vibentback.bubble.ownership;

import com.gitlab.vibent.vibentback.bubble.free.FreeBubble;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;


public interface BubbleOwnershipRepository extends CrudRepository<BubbleOwnership, Long> {

    BubbleOwnership findById(long id);
    ArrayList<BubbleOwnership> findByEventRef(String ref);

}
