package com.vibent.vibentback.bubble.survey;

import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.bubble.BubbleType;
import com.vibent.vibentback.bubble.survey.SurveyBubble;
import com.vibent.vibentback.bubble.survey.SurveyBubbleRepository;
import com.vibent.vibentback.bubble.survey.answer.SurveyAnswer;
import com.vibent.vibentback.bubble.survey.answer.SurveyAnswerRepository;
import com.vibent.vibentback.bubble.survey.usersAnswers.UsersSurveyAnswers;
import com.vibent.vibentback.bubble.survey.usersAnswers.UsersSurveyAnswersRepository;
import com.vibent.vibentback.event.EventRepository;
import com.vibent.vibentback.groupT.GroupTRepository;
import com.vibent.vibentback.user.User;
import com.vibent.vibentback.user.UserRepository;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@Slf4j
@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class SurveyDataTest extends VibentTest {

    @Autowired
    UsersSurveyAnswersRepository usersSurveyAnswersRepository;
    @Autowired
    SurveyBubbleRepository bubbleRepository;
    @Autowired
    SurveyAnswerRepository answerRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    EventRepository eventRepository;
    @Autowired
    GroupTRepository groupTRepository;

    SurveyAnswer RANDOM_ANSWER;
    SurveyBubble RANDOM_BUBBLE;
    UsersSurveyAnswers RANDOM_USER_ANSWER;

    @Before
    public void setUp()
    {
        super.setUp();

        RANDOM_USER = userRepository.save(RANDOM_USER);
        RANDOM_GROUP = groupTRepository.save(RANDOM_GROUP);
        RANDOM_EVENT = eventRepository.save(RANDOM_EVENT);

        log.info("{}",RANDOM_EVENT.getRef());

        RANDOM_BUBBLE = new SurveyBubble();
        RANDOM_BUBBLE.setDeleted(false);
        RANDOM_BUBBLE.setCreator(RANDOM_USER);
        RANDOM_BUBBLE.setEvent(RANDOM_EVENT);
        RANDOM_BUBBLE.setType(BubbleType.SurveyBubble);
        RANDOM_BUBBLE.setTitle("Title For Test");
        RANDOM_BUBBLE = bubbleRepository.save(RANDOM_BUBBLE);

        RANDOM_ANSWER = new SurveyAnswer();
        RANDOM_ANSWER.setDeleted(false);
        RANDOM_ANSWER.setUser(RANDOM_USER);
        RANDOM_ANSWER.setBubble(RANDOM_BUBBLE);
        RANDOM_ANSWER.setContent("Answer For Test");

        RANDOM_USER_ANSWER = new UsersSurveyAnswers();
        RANDOM_USER_ANSWER.setAnswer(RANDOM_ANSWER);
        RANDOM_USER_ANSWER.setUser(RANDOM_USER);
        RANDOM_USER_ANSWER.setDeleted(false);

    }

    @Test
    public void testAddSurveyBubble(){
        RANDOM_BUBBLE = bubbleRepository.save(RANDOM_BUBBLE);

        Assert.assertNotNull(bubbleRepository.findById(RANDOM_BUBBLE.getId()));
    }

    @Test
    public void testAddSurveyAnswer(){
        RANDOM_ANSWER = answerRepository.save(RANDOM_ANSWER);

        Assert.assertNotNull(answerRepository.findById(RANDOM_ANSWER.getId()));
    }

    @Test
    public void testAddUserAnswer() {
        RANDOM_USER_ANSWER.setAnswer(answerRepository.save(RANDOM_ANSWER));
        RANDOM_USER_ANSWER = usersSurveyAnswersRepository.save(RANDOM_USER_ANSWER);

        Assert.assertNotNull(usersSurveyAnswersRepository.findById(RANDOM_USER_ANSWER.getId()));
    }

}
