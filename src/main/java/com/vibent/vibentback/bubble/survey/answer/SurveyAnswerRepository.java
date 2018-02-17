package com.vibent.vibentback.bubble.survey.answer;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;


public interface SurveyAnswerRepository extends CrudRepository<SurveyAnswer, Long> {

    SurveyAnswer findById(long id);
    Iterable<SurveyAnswer> findByBubbleId(long id);
    Iterable<SurveyAnswer> findByCreatorRef(String ref);
    @Transactional
    int deleteById(long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE survey_answer SET deleted = FALSE WHERE id = :id", nativeQuery = true)
    int recover(@Param("id") long id);

    @Query(value = "SELECT deleted FROM survey_answer WHERE id = :id", nativeQuery = true)
    boolean isDeleted(@Param("id") long id);


}
