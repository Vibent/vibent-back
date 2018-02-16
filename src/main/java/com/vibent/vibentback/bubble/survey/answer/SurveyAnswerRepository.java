package com.vibent.vibentback.bubble.survey.answer;

import org.springframework.data.repository.CrudRepository;



public interface SurveyAnswerRepository extends CrudRepository<SurveyAnswer, Long> {

    SurveyAnswer findById(long id);
    Iterable<SurveyAnswer> findByBubbleId(long id);
    Iterable<SurveyAnswer> findByCreatorRef(String ref);

}
