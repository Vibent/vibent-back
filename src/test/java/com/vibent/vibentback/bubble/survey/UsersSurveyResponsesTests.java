package com.vibent.vibentback.bubble.survey;

import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.bubble.survey.*;
import com.vibent.vibentback.bubble.survey.response.SurveyResponse;
import com.vibent.vibentback.bubble.survey.response.SurveyResponseRepository;
import com.vibent.vibentback.bubble.survey.usersResponses.UsersSurveyResponses;
import com.vibent.vibentback.bubble.survey.usersResponses.UsersSurveyResponsesRepository;
import com.vibent.vibentback.user.User;
import com.vibent.vibentback.user.UserRepository;
import com.vibent.vibentback.bubble.survey.response.SurveyResponse;
import com.vibent.vibentback.bubble.survey.response.SurveyResponseRepository;
import com.vibent.vibentback.bubble.survey.usersResponses.UsersSurveyResponses;
import com.vibent.vibentback.bubble.survey.usersResponses.UsersSurveyResponsesRepository;
import com.vibent.vibentback.user.User;
import com.vibent.vibentback.user.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;



@RunWith(SpringRunner.class)
@SpringBootTest
public class UsersSurveyResponsesTests extends VibentTest {

    @Autowired
    UsersSurveyResponsesRepository repository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SurveyResponseRepository surveyResponseRepository;

    @Autowired
    SurveyBubbleRepository surveyBubbleRepository;

    User user;
    SurveyResponse surveyResponse;
     SurveyBubble surveyBubble;

    @Before
    public void init()
    {
        /** User creation **/
        user = new User(UUID.randomUUID().toString(),"conor","ryan","cr.sd.sd@gmail.com","secret","sel");
        userRepository.save(user);
        /** Bubble creation **/
        surveyBubble = new SurveyBubble("surveyBubbleTitle");
        surveyBubbleRepository.save(surveyBubble);
        /** Bubble response creation **/
        surveyResponse = new SurveyResponse(surveyBubble.getId(), user.getRef(),"response");
        surveyResponseRepository.save(surveyResponse);
    }

    @Test
    public void testAddUsersSurveyResponses(){
        UsersSurveyResponses usersSurveyResponse = new UsersSurveyResponses(user.getRef(), surveyResponse.getId());
        repository.save(usersSurveyResponse);
    }
}
