package com.gitlab.vibent.vibentback.bubble.survey.response;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface SurveyResponseRepository extends CrudRepository<SurveyResponse, Long> {

    SurveyResponse findById(long id);
    ArrayList<SurveyResponse> findByBubbleId(long id);
    ArrayList<SurveyResponse> findByCreatorRef(String ref);

}
