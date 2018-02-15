package com.vibent.vibentback.bubble.checkbox;

import org.springframework.data.repository.CrudRepository;

public interface CheckboxBubbleRepository extends CrudRepository<CheckboxBubble, Long> {

    CheckboxBubble findById(long id);
    Iterable<CheckboxBubble> findByTitle(String title);

}
