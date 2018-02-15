package com.vibent.vibentback.bubble.free;

import org.springframework.data.repository.CrudRepository;

public interface FreeBubbleRepository extends CrudRepository<FreeBubble, Long> {

    FreeBubble findById(long id);
    Iterable<FreeBubble> findByTitle(String title);

}
