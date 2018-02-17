package com.vibent.vibentback.bubble.checkbox.answer;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface CheckboxAnswerRepository extends CrudRepository<CheckboxAnswer, Long> {

    CheckboxAnswer findById(long id);
    Iterable<CheckboxAnswer> findByBubbleId(long id);

    @Transactional
    int deleteById(long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE checkbox_answers SET deleted = FALSE WHERE id = :id", nativeQuery = true)
    int recover(@Param("id") long id);

    @Query(value = "SELECT deleted FROM checkbox_answers WHERE id = :id", nativeQuery = true)
    boolean isDeleted(@Param("id") long id);
}
