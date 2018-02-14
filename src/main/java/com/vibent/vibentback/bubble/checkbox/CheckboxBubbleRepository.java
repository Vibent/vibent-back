package com.vibent.vibentback.bubble.checkbox;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;


public interface CheckboxBubbleRepository extends CrudRepository<CheckboxBubble, Long> {

    CheckboxBubble findById(long id);
    ArrayList<CheckboxBubble> findByTitle(String title);

}
