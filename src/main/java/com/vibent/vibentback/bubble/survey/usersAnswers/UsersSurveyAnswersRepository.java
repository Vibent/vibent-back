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

    @Query(value = "SELECT bubble.id FROM survey_bubble bubble\n" +
            "JOIN survey_answer answer ON bubble.id = answer.bubble_id\n" +
            "JOIN users_survey_answers userAnswer ON answer.id = userAnswer.survey_answer_id\n" +
            "WHERE userAnswer.id = :id", nativeQuery = true)
    Long getBubbleId(@Param("id") Long id);
}
