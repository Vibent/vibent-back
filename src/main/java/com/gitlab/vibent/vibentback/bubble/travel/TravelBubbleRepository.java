package com.gitlab.vibent.vibentback.bubble.travel;

import org.springframework.data.repository.CrudRepository;

public interface TravelBubbleRepository extends CrudRepository<TravelBubble, Long> {

    TravelBubble findById(long id);

}
