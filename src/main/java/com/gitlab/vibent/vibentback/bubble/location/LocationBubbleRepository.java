package com.gitlab.vibent.vibentback.bubble.location;


import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;


public interface LocationBubbleRepository extends CrudRepository<LocationBubble, Long> {

    LocationBubble findById(long id);

}
