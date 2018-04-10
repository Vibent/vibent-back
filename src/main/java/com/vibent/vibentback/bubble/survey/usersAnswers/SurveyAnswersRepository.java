package com.vibent.vibentback.bubble.survey.usersAnswers;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;


public interface SurveyAnswersRepository extends CrudRepository<SurveyAnswer, Long> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE survey_answer SET deleted = FALSE WHERE id = :id", nativeQuery = true)
    int recover(@Param("id") long id);

    @Query(value = "SELECT deleted FROM survey_answer WHERE id = :id", nativeQuery = true)
    boolean isDeleted(@Param("id") long id);
}
