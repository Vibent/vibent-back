package com.vibent.vibentback.bubble.checkbox.usersAnswers;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface UsersCheckboxAnswersRepository extends CrudRepository<UsersCheckboxAnswers, Long> {

    UsersCheckboxAnswers findById(long id);
    Iterable<UsersCheckboxAnswers> findByCheckboxAnswerId(long id);

    @Transactional
    int deleteById(long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users_checkbox_answers SET deleted = FALSE WHERE id = :id", nativeQuery = true)
    int recover(@Param("id") long id);

    @Query(value = "SELECT deleted FROM users_checkbox_answers WHERE id = :id", nativeQuery = true)
    boolean isDeleted(@Param("id") long id);

}
