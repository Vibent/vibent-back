package com.vibent.vibentback.bubble.survey;

import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.api.bubble.survey.SurveyBubbleRequest;
import com.vibent.vibentback.bubble.BubbleType;
import com.vibent.vibentback.bubble.survey.answer.SurveyAnswer;
import com.vibent.vibentback.bubble.survey.usersAnswers.UsersSurveyAnswers;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;

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
    SurveyBubbleRequest RANDOM_REQ;



    @Before
    public void setUp() {
        super.setUp();
        RANDOM_BUBBLE = new SurveyBubble();
        RANDOM_ANSWER = new SurveyAnswer();
        RANDOM_USER_ANSWER = new UsersSurveyAnswers();

        RANDOM_REQ = new SurveyBubbleRequest();
        RANDOM_REQ.setEventRef(RANDOM_EVENT.getRef());


        RANDOM_BUBBLE.setId(666L);
        RANDOM_BUBBLE.setAnswers(new HashSet<SurveyAnswer>(){{
            add(RANDOM_ANSWER);
        }});
        RANDOM_BUBBLE.setType(BubbleType.SurveyBubble);
        RANDOM_BUBBLE.setEvent(RANDOM_EVENT);
        RANDOM_BUBBLE.setCreator(RANDOM_USER);
        RANDOM_BUBBLE.setDeleted(false);

        RANDOM_ANSWER.setId(123L);
        RANDOM_ANSWER.setBubble(RANDOM_BUBBLE);
        RANDOM_ANSWER.setDeleted(false);
        RANDOM_ANSWER.setContent("Answer Content For Test");
        RANDOM_ANSWER.setUser(RANDOM_USER);
        RANDOM_ANSWER.setUsersAnswers(new HashSet<UsersSurveyAnswers>(){{
            add(RANDOM_USER_ANSWER);
        }});

        RANDOM_USER_ANSWER.setId(546L);
        RANDOM_USER_ANSWER.setAnswer(RANDOM_ANSWER);
        RANDOM_USER_ANSWER.setDeleted(false);
        RANDOM_USER_ANSWER.setUser(RANDOM_USER);

        when(service.getBubble(RANDOM_BUBBLE.getId())).thenReturn(RANDOM_BUBBLE);
        when(service.createBubble(RANDOM_EVENT.getRef())).thenReturn(RANDOM_BUBBLE);}

    @Test
    public void testGetBubble() throws Exception {
        this.mockMvc.perform(get(ROOT_URL + "/" + RANDOM_BUBBLE.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(EXPECTED_CONTAIN)));
    }

    @Test
    public void testCreateBubble() throws Exception {
        String body = super.getJsonString(RANDOM_REQ);

        mockMvc.perform(post(ROOT_URL).contentType(APPLICATION_JSON_UTF8)
                .content(body))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString(EXPECTED_CONTAIN)));}


    @Test
    public void testDeleteBubble() throws Exception {
        mockMvc.perform(delete(ROOT_URL + "/" + RANDOM_BUBBLE.getId()))
                .andExpect(status().isNoContent());
    }
/*
    @Test
    public void testCreateAnswer() throws Exception {
        String body = super.getJsonString(RANDOM_ANSWER_REQUEST);

        mockMvc.perform(post(ROOT_URL + "/entry").contentType(APPLICATION_JSON_UTF8)
                .content(body))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString(EXPECTED_CONTAIN)));
    }

    @Test
    public void testDeleteAnswer() throws Exception {
        mockMvc.perform(delete(ROOT_URL + "/entry/" + RANDOM_ANSWER.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testUpdateAnswer() throws Exception {
        String body = super.getJsonString(RANDOM_ANSWER_UPDATE_REQUEST);

        mockMvc.perform(patch(ROOT_URL + "/entry/" + RANDOM_ANSWER.getId())
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
    }*/
}
