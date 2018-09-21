package com.vibent.vibentback.bubble.survey.option;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface SurveyOptionRepository extends CrudRepository<SurveyOption, Long> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE survey_option SET deleted = FALSE WHERE id = :id", nativeQuery = true)
    int recover(@Param("id") long id);

    @Query(value = "SELECT deleted FROM survey_option WHERE id = :id", nativeQuery = true)
    boolean isDeleted(@Param("id") long id);
}
