package com.gitlab.vibent.vibentback.bubble.free;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;


public interface FreeBubbleRepository extends CrudRepository<FreeBubble, Long> {

    FreeBubble findById(long id);
    ArrayList<FreeBubble> findByTitle(String title);

}
