package com.vibent.vibentback.bubble.survey;

import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.api.bubble.survey.*;
import com.vibent.vibentback.bubble.BubbleType;
import com.vibent.vibentback.bubble.alimentation.AlimentationController;
import com.vibent.vibentback.bubble.ownership.BubbleOwnership;
import com.vibent.vibentback.bubble.survey.answer.SurveyAnswer;
import com.vibent.vibentback.bubble.survey.answer.SurveyAnswerRepository;
import com.vibent.vibentback.bubble.survey.usersAnswers.UsersSurveyAnswers;
import com.vibent.vibentback.bubble.survey.usersAnswers.UsersSurveyAnswersRepository;
import com.vibent.vibentback.user.User;
import com.vibent.vibentback.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Slf4j
@RunWith(SpringRunner.class)
@WebMvcTest(SurveyController.class)
public class SurveyWebLayerTest extends VibentTest {
    private final static String ROOT_URL = "/bubble/survey";
    /**
     * String used to check the response (i.e. response body must contain this string)
     */
    private final static String EXPECTED_CONTAIN = "\"answers\"";

    @Autowired
    public MockMvc mockMvc;

    @MockBean
    SurveyService service;

    SurveyBubble RANDOM_BUBBLE;
    SurveyAnswer RANDOM_ANSWER;
    UsersSurveyAnswers RANDOM_USER_ANSWER;
    BubbleOwnership RANDOM_OWNERSHIP;
    SurveyBubbleReq RANDOM_BUBBLE_REQUEST;
    SurveyBubbleRes RANDOM_RESPONSE;
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

        when(service.getBubble(RANDOM_BUBBLE.getId())).thenReturn(RANDOM_RESPONSE);
        when(service.createBubble(RANDOM_BUBBLE_REQUEST)).thenReturn(RANDOM_RESPONSE);
        when(service.updateBubble(RANDOM_BUBBLE.getId(), RANDOM_BUBBLE_UPDATE_REQUEST)).thenReturn(RANDOM_RESPONSE);
        when(service.createBubbleAnswer(RANDOM_ANSWER_REQUEST)).thenReturn(RANDOM_RESPONSE);
        when(service.updateBubbleAnswer(RANDOM_ANSWER.getId(), RANDOM_ANSWER_UPDATE_REQUEST)).thenReturn(RANDOM_RESPONSE);
        when(service.createBubbleUserAnswer(RANDOM_USER_ANSWER_REQUEST)).thenReturn(RANDOM_RESPONSE);
        when(service.updateBubble(RANDOM_BUBBLE.getId(), RANDOM_BUBBLE_UPDATE_REQUEST)).thenReturn(RANDOM_RESPONSE);
    }

    @Test
    public void testGetBubble() throws Exception {
        this.mockMvc.perform(get(ROOT_URL + "/" + RANDOM_BUBBLE.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(EXPECTED_CONTAIN)));
    }

    @Test
    public void testCreateBubble() throws Exception {
        String body = super.getJsonString(RANDOM_BUBBLE_REQUEST);

        mockMvc.perform(post(ROOT_URL).contentType(APPLICATION_JSON_UTF8)
                .content(body))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString(EXPECTED_CONTAIN)));
    }


    @Test
    public void testDeleteBubble() throws Exception {
        mockMvc.perform(delete(ROOT_URL + "/" + RANDOM_BUBBLE.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testCreateAnswer() throws Exception {
        String body = super.getJsonString(RANDOM_ANSWER_REQUEST);

        mockMvc.perform(post(ROOT_URL + "/answer").contentType(APPLICATION_JSON_UTF8)
                .content(body))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString(EXPECTED_CONTAIN)));
    }

    @Test
    public void testDeleteAnswer() throws Exception {
        mockMvc.perform(delete(ROOT_URL + "/answer/" + RANDOM_ANSWER.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testUpdateAnswer() throws Exception {
        String body = super.getJsonString(RANDOM_ANSWER_UPDATE_REQUEST);

        mockMvc.perform(patch(ROOT_URL + "/answer/" + RANDOM_ANSWER.getId())
                .contentType(APPLICATION_JSON_UTF8).content(body))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(EXPECTED_CONTAIN)));
    }

    @Test
    public void testCreateUserAnswer() throws Exception {
        String body = super.getJsonString(RANDOM_USER_ANSWER);

        mockMvc.perform(post(ROOT_URL + "/useranswer").contentType(APPLICATION_JSON_UTF8)
                .content(body))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString(EXPECTED_CONTAIN)));
    }

    @Test
    public void testDeleteBring() throws Exception {
        mockMvc.perform(delete(ROOT_URL + "/useranswer/" + RANDOM_USER_ANSWER.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testUpdateBubble() throws Exception {
        String body = super.getJsonString(RANDOM_BUBBLE_UPDATE_REQUEST);

        mockMvc.perform(patch(ROOT_URL + "/" + RANDOM_BUBBLE.getId())
                .contentType(APPLICATION_JSON_UTF8).content(body))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(EXPECTED_CONTAIN)));
    }
}
