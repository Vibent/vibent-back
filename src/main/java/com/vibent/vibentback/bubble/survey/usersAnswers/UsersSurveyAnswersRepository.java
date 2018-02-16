package com.vibent.vibentback.bubble.survey.usersAnswers;

import org.springframework.data.repository.CrudRepository;



public interface UsersSurveyAnswersRepository extends CrudRepository<UsersSurveyAnswers, Long> {

    UsersSurveyAnswers findById(long id);
    Iterable<UsersSurveyAnswers> findBySurveyAnswerId(long id);
    Iterable<UsersSurveyAnswers> findByUserRef(String ref);

}
