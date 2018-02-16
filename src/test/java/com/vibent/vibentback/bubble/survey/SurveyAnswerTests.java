package com.vibent.vibentback.bubble.survey;

import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.bubble.survey.answer.SurveyAnswer;
import com.vibent.vibentback.bubble.survey.answer.SurveyAnswerRepository;
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
public class SurveyAnswerTests extends VibentTest {

    @Autowired
    SurveyAnswerRepository repository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SurveyBubbleRepository surveyRepository;


    User user;
    SurveyBubble surveyBubble;

    @Before
    public void init()
    {
        /** User creation **/
        user = new User(UUID.randomUUID().toString(),"conor","ryan","cr.sd.sd@gmail.com","secret","sel");
        userRepository.save(user);
        /** Bubble creation **/
        surveyBubble = new SurveyBubble("survetTest");
        surveyRepository.save(surveyBubble);
    }

    @Test
    public void testAddSurveyAnswer(){
        SurveyAnswer surveyAnswer = new SurveyAnswer( surveyBubble.getId(),user.getRef(),"answerContent");
        repository.save(surveyAnswer);
    }
}
