package com.gitlab.vibent.vibentback.bubble.alimentation;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;


public interface AlimentationBubbleRepository extends CrudRepository<AlimentationBubble, Long> {

    AlimentationBubble findById(long id);

}
