package com.vibent.vibentback.bubble.checkbox.usersAnswers;


import org.springframework.data.repository.CrudRepository;

public interface UsersCheckboxAnswersRepository extends CrudRepository<UsersCheckboxAnswers, Long> {

    UsersCheckboxAnswers findById(long id);
    Iterable<UsersCheckboxAnswers> findByCheckboxAnswerId(long id);

}
