package com.vibent.vibentback.bubble.survey.usersAnswers;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;


public interface UsersSurveyAnswersRepository extends CrudRepository<UsersSurveyAnswers, Long> {

    UsersSurveyAnswers findById(long id);
    Iterable<UsersSurveyAnswers> findBySurveyAnswerId(long id);
    Iterable<UsersSurveyAnswers> findByUserRef(String ref);

    @Transactional
    int deleteById(long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users_survey_answers SET deleted = FALSE WHERE id = :id", nativeQuery = true)
    int recover(@Param("id") long id);

    @Query(value = "SELECT deleted FROM users_survey_answers WHERE id = :id", nativeQuery = true)
    boolean isDeleted(@Param("id") long id);
}
