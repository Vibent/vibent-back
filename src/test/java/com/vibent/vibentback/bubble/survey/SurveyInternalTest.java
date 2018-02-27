package com.vibent.vibentback.bubble.survey;

import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.api.bubble.survey.*;
import com.vibent.vibentback.bubble.BubbleType;
import com.vibent.vibentback.bubble.ownership.BubbleOwnership;
import com.vibent.vibentback.bubble.ownership.BubbleOwnershipRepository;
import com.vibent.vibentback.bubble.survey.answer.SurveyAnswer;
import com.vibent.vibentback.bubble.survey.answer.SurveyAnswerRepository;
import com.vibent.vibentback.bubble.survey.usersAnswers.UsersSurveyAnswers;
import com.vibent.vibentback.bubble.survey.usersAnswers.UsersSurveyAnswersRepository;
import com.vibent.vibentback.error.VibentException;
import com.vibent.vibentback.event.EventRepository;
import com.vibent.vibentback.user.User;
import com.vibent.vibentback.user.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SurveyInternalTest extends VibentTest {

    @Autowired
    @InjectMocks
    private SurveyController controller;

    @MockBean
    private SurveyBubbleRepository bubbleRepository;
    @MockBean
    private SurveyAnswerRepository surveyAnswerRepository;
    @MockBean
    private UsersSurveyAnswersRepository usersSurveyAnswersRepository;
    @MockBean
    private BubbleOwnershipRepository ownershipRepository;
    @MockBean
    private EventRepository eventRepository;

    SurveyBubble RANDOM_BUBBLE;
    SurveyAnswer RANDOM_ANSWER;
    UsersSurveyAnswers RANDOM_USER_ANSWER;
    BubbleOwnership RANDOM_OWNERSHIP;
    SurveyBubbleReq RANDOM_BUBBLE_REQUEST;
    SurveyAnswerReq RANDOM_ANSWER_REQUEST;
    SurveyAnswerUpdateReq RANDOM_ANSWER_UPDATE_REQUEST;
    UsersSurveyAnswersReq RANDOM_USER_ANSWER_REQUEST;
    SurveyBubbleUpdateReq RANDOM_BUBBLE_UPDATE_REQUEST;


    @Before
    public void setUp() {
        super.setUp();
        MockitoAnnotations.initMocks(this);

        RANDOM_BUBBLE = new SurveyBubble();
        RANDOM_BUBBLE.setId(666L);
        RANDOM_BUBBLE.setTitle("title");

        RANDOM_ANSWER = new SurveyAnswer();
        RANDOM_ANSWER.setId(123L);
        RANDOM_ANSWER.setBubbleId(RANDOM_BUBBLE.getId());
        RANDOM_ANSWER.setDeleted(false);
        RANDOM_ANSWER.setContent("Response");
        RANDOM_ANSWER.setCreatorRef(RANDOM_USER.getRef());

        RANDOM_USER_ANSWER = new UsersSurveyAnswers();
        RANDOM_USER_ANSWER.setId(546L);
        RANDOM_USER_ANSWER.setSurveyAnswerId(RANDOM_ANSWER.getId());
        RANDOM_USER_ANSWER.setDeleted(false);
        RANDOM_USER_ANSWER.setUserRef(RANDOM_USER.getRef());

        RANDOM_OWNERSHIP = new BubbleOwnership();
        RANDOM_OWNERSHIP.setId(789L);
        RANDOM_OWNERSHIP.setDeleted(false);
        RANDOM_OWNERSHIP.setCreatorRef(RANDOM_USER.getRef());
        RANDOM_OWNERSHIP.setBubbleId(RANDOM_BUBBLE.getId());
        RANDOM_OWNERSHIP.setEventRef(RANDOM_EVENT.getRef());
        RANDOM_OWNERSHIP.setBubbleType(BubbleType.SurveyBubble);

        RANDOM_BUBBLE_REQUEST = new SurveyBubbleReq();
        RANDOM_BUBBLE_REQUEST.setEventRef(RANDOM_EVENT.getRef());

        RANDOM_ANSWER_REQUEST = new SurveyAnswerReq(RANDOM_BUBBLE.getId(),RANDOM_USER.getRef(),"content");

        RANDOM_ANSWER_UPDATE_REQUEST = new SurveyAnswerUpdateReq("content");

        RANDOM_USER_ANSWER_REQUEST = new UsersSurveyAnswersReq(RANDOM_USER.getRef(), RANDOM_ANSWER.getId());

        RANDOM_BUBBLE_UPDATE_REQUEST = new SurveyBubbleUpdateReq();
        RANDOM_BUBBLE_REQUEST = new SurveyBubbleReq();
        RANDOM_BUBBLE_REQUEST.setTitle("newTitle");

        when(bubbleRepository.findById(RANDOM_BUBBLE.getId())).thenReturn(Optional.of(RANDOM_BUBBLE));
        when(bubbleRepository.existsById(RANDOM_BUBBLE.getId())).thenReturn(true);
        when(bubbleRepository.save(new SurveyBubble())).thenReturn(RANDOM_BUBBLE);
        when(eventRepository.findByRef(RANDOM_EVENT.getRef())).thenReturn(Optional.of(RANDOM_EVENT));
        when(ownershipRepository.findByIdAndBubbleType(RANDOM_BUBBLE.getId(), BubbleType.SurveyBubble))
                .thenReturn(Optional.of(RANDOM_OWNERSHIP));
        when(surveyAnswerRepository.findById(RANDOM_ANSWER.getId())).thenReturn(Optional.of(RANDOM_ANSWER));
        when(surveyAnswerRepository.save(RANDOM_ANSWER)).thenReturn(RANDOM_ANSWER);
        when(surveyAnswerRepository.existsById(RANDOM_ANSWER.getId())).thenReturn(true);
        when(usersSurveyAnswersRepository.findById(RANDOM_USER_ANSWER.getId())).thenReturn(Optional.of(RANDOM_USER_ANSWER));
        when(usersSurveyAnswersRepository.save(RANDOM_USER_ANSWER)).thenReturn(RANDOM_USER_ANSWER);
        when(usersSurveyAnswersRepository.existsById(RANDOM_USER_ANSWER.getId())).thenReturn(true);
        when(usersSurveyAnswersRepository.getBubbleId(RANDOM_USER_ANSWER.getId())).thenReturn(RANDOM_BUBBLE.getId());
    }

    @Test
    public void getBubble() {
        SurveyBubble bubble = controller.getBubble(RANDOM_BUBBLE.getId());
        Assert.assertEquals(RANDOM_BUBBLE.getId(), bubble.getId());
    }

    @Test
    public void getNonExistingBubble() throws VibentException {
        exception.expect(VibentException.class);
        exception.expectMessage("bubble-not-found");

        controller.getBubble(-1L);
    }

    @Test
    public void createBubble() {
        SurveyBubbleRes bubble = controller.createBubble(RANDOM_BUBBLE_REQUEST);
        Assert.assertEquals(bubble.getId(), bubble.getId());
    }

    @Test
    public void createBubbleWithWrongEventRef() {
        exception.expect(VibentException.class);
        exception.expectMessage("event-not-found");

        SurveyBubbleRes bubble = controller.createBubble(new SurveyBubbleReq());
    }

    @Test
    public void deleteBubble() {
        controller.deleteBubble(RANDOM_BUBBLE.getId());
    }

    @Test
    public void deleteNonExistingBubble() {
        exception.expect(VibentException.class);
        exception.expectMessage("bubble-not-found");

        controller.deleteBubble(-1L);
    }

    @Test
    public void updateBubble(){
        SurveyBubbleRes res = controller.updateBubble(RANDOM_BUBBLE.getId(), RANDOM_BUBBLE_UPDATE_REQUEST);
        Assert.assertNotNull(res);
    }

    @Test
    public void updateBubbleWrongId(){
        exception.expect(VibentException.class);
        exception.expectMessage("bubble-not-found");

        controller.updateBubble(-1L, RANDOM_BUBBLE_UPDATE_REQUEST);
    }

    // answer
    @Test
    public void createBubbleAnswer(){
        SurveyBubbleRes res = controller.createBubbleAnswer(RANDOM_ANSWER_REQUEST);
        Assert.assertEquals(res.getId(), RANDOM_BUBBLE.getId());
    }

    @Test
    public void updateBubbleAnswer(){
        SurveyBubbleRes res = controller.updateBubbleAnswer(RANDOM_ANSWER.getId(), RANDOM_ANSWER_UPDATE_REQUEST);
        Assert.assertNotNull(res);
    }

    @Test
    public void updateBubbleAnswerWrongId(){
        exception.expect(VibentException.class);
        exception.expectMessage("answer-not-found");

        controller.updateBubbleAnswer(-1L, RANDOM_ANSWER_UPDATE_REQUEST);
    }

    @Test
    public void deleteAnswer(){
        controller.deleteBubbleAnswer(RANDOM_ANSWER.getId());
    }

    @Test
    public void deleteAnswerWrongId(){
        exception.expect(VibentException.class);
        exception.expectMessage("answer-not-found");

        controller.deleteBubbleAnswer(-1L);
    }

    // UserAnswer
    @Test
    public void createBubbleUserAnswer(){
        SurveyBubbleRes res = controller.createBubbleUserAnswer(RANDOM_USER_ANSWER_REQUEST);
        Assert.assertEquals(res.getId(), RANDOM_BUBBLE.getId());
    }

    @Test
    public void deleteUserAnswer(){
        controller.deleteBubbleUserAnswer(RANDOM_USER_ANSWER.getId());
    }

    @Test
    public void deleteUserAnswerWrongId(){
        exception.expect(VibentException.class);
        exception.expectMessage("user-answer-not-found");

        controller.deleteBubbleUserAnswer(-1L);
    }

}
