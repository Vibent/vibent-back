package com.vibent.vibentback.bubble.survey.usersResponses;

import org.springframework.data.repository.CrudRepository;



public interface UsersSurveyResponsesRepository extends CrudRepository<UsersSurveyResponses, Long> {

    UsersSurveyResponses findById(long id);
    Iterable<UsersSurveyResponses> findBySurveyResponseId(long id);
    Iterable<UsersSurveyResponses> findByUserRef(String ref);

}
