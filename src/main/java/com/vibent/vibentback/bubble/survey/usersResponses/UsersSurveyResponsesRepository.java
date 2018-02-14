package com.vibent.vibentback.bubble.survey.usersResponses;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface UsersSurveyResponsesRepository extends CrudRepository<UsersSurveyResponses, Long> {

    UsersSurveyResponses findById(long id);
    ArrayList<UsersSurveyResponses> findBySurveyResponseId(long id);
    ArrayList<UsersSurveyResponses> findByUserRef(String ref);

}
