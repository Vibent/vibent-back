package com.vibent.vibentback.bubble.location;

import org.springframework.data.repository.CrudRepository;

public interface LocationBubbleRepository extends CrudRepository<LocationBubble, Long> {

    LocationBubble findById(long id);

}
