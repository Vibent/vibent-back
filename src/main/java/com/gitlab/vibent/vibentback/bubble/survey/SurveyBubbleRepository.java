package com.gitlab.vibent.vibentback.bubble.survey;

import org.springframework.data.repository.CrudRepository;

public interface SurveyBubbleRepository extends CrudRepository<SurveyBubble, Long> {

    SurveyBubble findById(long id);

}
