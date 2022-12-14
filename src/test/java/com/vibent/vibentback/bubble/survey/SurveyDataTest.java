package com.vibent.vibentback.bubble.survey;

import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.bubble.survey.answer.SurveyAnswer;
import com.vibent.vibentback.bubble.survey.answer.SurveyAnswerRepository;
import com.vibent.vibentback.bubble.survey.option.SurveyOption;
import com.vibent.vibentback.bubble.survey.option.SurveyOptionRepository;
import com.vibent.vibentback.event.EventRepository;
import com.vibent.vibentback.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

@Slf4j
@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class SurveyDataTest extends VibentTest {

    @Autowired
    SurveyAnswerRepository surveyAnswerRepository;
    @Autowired
    SurveyBubbleRepository bubbleRepository;
    @Autowired
    SurveyOptionRepository responseRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    EventRepository eventRepository;

    SurveyOption RANDOM_OPTION;
    SurveyBubble RANDOM_BUBBLE;
    SurveyAnswer RANDOM_ANSWER;

    @Before
    public void setUp() {
        super.setUp();

        RANDOM_USER = userRepository.save(RANDOM_USER);
        RANDOM_EVENT = eventRepository.save(RANDOM_EVENT);

        RANDOM_BUBBLE = new SurveyBubble();
        RANDOM_BUBBLE.setDeleted(false);
        RANDOM_BUBBLE.setCreator(RANDOM_USER);
        RANDOM_BUBBLE.setEvent(RANDOM_EVENT);
        RANDOM_BUBBLE.setTitle("Title For Test");
        RANDOM_BUBBLE = bubbleRepository.save(RANDOM_BUBBLE);

        RANDOM_OPTION = new SurveyOption();
        RANDOM_OPTION.setDeleted(false);
        RANDOM_OPTION.setUser(RANDOM_USER);
        RANDOM_OPTION.setBubble(RANDOM_BUBBLE);
        RANDOM_OPTION.setContent("Response For Test");

        RANDOM_ANSWER = new SurveyAnswer();
        RANDOM_ANSWER.setOption(RANDOM_OPTION);
        RANDOM_ANSWER.setUser(RANDOM_USER);
        RANDOM_ANSWER.setDeleted(false);

    }

    @Test
    public void testAddSurveyBubble() {
        RANDOM_BUBBLE = bubbleRepository.save(RANDOM_BUBBLE);

        Assert.assertNotNull(bubbleRepository.findById(RANDOM_BUBBLE.getId()));
    }

    @Test
    public void testAddSurveyResponse() {
        RANDOM_OPTION = responseRepository.save(RANDOM_OPTION);

        Assert.assertNotNull(responseRepository.findById(RANDOM_OPTION.getId()));
    }

    @Test
    public void testAddUserResponse() {
        RANDOM_ANSWER.setOption(responseRepository.save(RANDOM_OPTION));
        RANDOM_ANSWER = surveyAnswerRepository.save(RANDOM_ANSWER);

        Assert.assertNotNull(surveyAnswerRepository.findById(RANDOM_ANSWER.getId()));
    }
}
