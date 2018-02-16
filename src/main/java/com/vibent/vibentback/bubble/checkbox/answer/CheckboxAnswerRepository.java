package com.vibent.vibentback.bubble.checkbox.answer;

import org.springframework.data.repository.CrudRepository;

public interface CheckboxAnswerRepository extends CrudRepository<CheckboxAnswer, Long> {

    CheckboxAnswer findById(long id);
    Iterable<CheckboxAnswer> findByBubbleId(long id);

}
