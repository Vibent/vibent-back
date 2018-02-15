package com.vibent.vibentback.bubble.survey.response;

import org.springframework.data.repository.CrudRepository;



public interface SurveyResponseRepository extends CrudRepository<SurveyResponse, Long> {

    SurveyResponse findById(long id);
    Iterable<SurveyResponse> findByBubbleId(long id);
    Iterable<SurveyResponse> findByCreatorRef(String ref);

}
