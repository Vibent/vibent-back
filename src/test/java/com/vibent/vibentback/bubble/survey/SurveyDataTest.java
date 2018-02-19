package com.vibent.vibentback.bubble.survey;

import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.bubble.survey.SurveyBubble;
import com.vibent.vibentback.bubble.survey.SurveyBubbleRepository;
import com.vibent.vibentback.bubble.survey.answer.SurveyAnswer;
import com.vibent.vibentback.bubble.survey.answer.SurveyAnswerRepository;
import com.vibent.vibentback.bubble.survey.usersAnswers.UsersSurveyAnswers;
import com.vibent.vibentback.bubble.survey.usersAnswers.UsersSurveyAnswersRepository;
import com.vibent.vibentback.user.User;
import com.vibent.vibentback.user.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SurveyDataTest extends VibentTest {

    @Autowired
    UsersSurveyAnswersRepository usersSurveyAnswersRepository;
    @Autowired
    SurveyBubbleRepository bubbleRepository;
    @Autowired
    SurveyAnswerRepository answerRepository;
    @Autowired
    UserRepository userRepository;

    private User user;
    private SurveyBubble surveyBubble;
    private SurveyAnswer surveyAnswer;

    @Before
    public void setUp()
    {
        super.setUp();
        /** User creation **/
        user = userRepository.save(RANDOM_USER);
        /** Bubble creation **/
        surveyBubble = bubbleRepository.save(new SurveyBubble("title"));
        /** Answer creation **/
        surveyAnswer = answerRepository.save(new SurveyAnswer(surveyBubble.getId(), user.getRef(), "answer"));
    }

    @Test
    public void testAddUserAnswer() {
        UsersSurveyAnswers usersSurveyAnswers = new UsersSurveyAnswers(user.getRef(), surveyAnswer.getId());
        usersSurveyAnswersRepository.save(usersSurveyAnswers);
    }

    @Test
    public void testAddSurveyBubble(){
        SurveyBubble surveyBubble = new SurveyBubble();
        bubbleRepository.save(surveyBubble);
    }

    @Test
    public void testAddSurveyAnswer(){
        SurveyAnswer surveyAnswer = new SurveyAnswer(surveyBubble.getId(), user.getRef(),"answer2");
        answerRepository.save(surveyAnswer);
    }

}
