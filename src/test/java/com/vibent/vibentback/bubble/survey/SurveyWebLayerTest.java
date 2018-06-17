package com.vibent.vibentback.bubble.survey;

import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.api.bubble.survey.*;
import com.vibent.vibentback.bubble.BubbleType;
import com.vibent.vibentback.bubble.survey.option.SurveyOption;
import com.vibent.vibentback.bubble.survey.usersAnswers.SurveyAnswer;
import lombok.extern.slf4j.Slf4j;
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
@WebMvcTest(value = SurveyController.class, secure = false)
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
    SurveyOption RANDOM_OPTION;
    SurveyAnswer RANDOM_ANSWER;
    SurveyBubbleRequest RANDOM_BUBBLE_REQUEST;
    SurveyOptionRequest RANDOM_OPTION_REQUEST;
    SurveyOptionUpdateRequest RANDOM_OPTION_UPDATE_REQUEST;
    SurveyBubbleUpdateRequest RANDOM_BUBBLE_UPDATE_REQUEST;
    SurveyAnswerRequest RANDOM_ANSWER_REQUEST;

    @Before
    public void setUp() {
        super.setUp();
        RANDOM_BUBBLE = new SurveyBubble();
        RANDOM_OPTION = new SurveyOption();
        RANDOM_ANSWER = new SurveyAnswer();

        RANDOM_BUBBLE.setId(666L);
        RANDOM_BUBBLE.setOptions(new HashSet<SurveyOption>(){{
            add(RANDOM_OPTION);
        }});
        RANDOM_BUBBLE.setType(BubbleType.SurveyBubble);
        RANDOM_BUBBLE.setEvent(RANDOM_EVENT);
        RANDOM_BUBBLE.setCreator(RANDOM_USER);
        RANDOM_BUBBLE.setDeleted(false);

        RANDOM_BUBBLE_REQUEST = new SurveyBubbleRequest();
        RANDOM_BUBBLE_REQUEST.setEventRef(RANDOM_EVENT.getRef());
        RANDOM_BUBBLE_REQUEST.setTitle("Title for bubble");

        RANDOM_BUBBLE_UPDATE_REQUEST = new SurveyBubbleUpdateRequest();
        RANDOM_BUBBLE_UPDATE_REQUEST.setTitle("New title");

        RANDOM_OPTION.setId(123L);
        RANDOM_OPTION.setBubble(RANDOM_BUBBLE);
        RANDOM_OPTION.setDeleted(false);
        RANDOM_OPTION.setContent("Answer Content For Test");
        RANDOM_OPTION.setUser(RANDOM_USER);
        RANDOM_OPTION.setAnswers(new HashSet<SurveyAnswer>(){{
            add(RANDOM_ANSWER);
        }});

        RANDOM_OPTION_REQUEST = new SurveyOptionRequest();
        RANDOM_OPTION_REQUEST.setBubbleId(RANDOM_BUBBLE.getId());
        RANDOM_OPTION_REQUEST.setContent("Here is content");

        RANDOM_OPTION_UPDATE_REQUEST = new SurveyOptionUpdateRequest();
        RANDOM_OPTION_UPDATE_REQUEST.setContent("Here is content");

        RANDOM_ANSWER.setId(546L);
        RANDOM_ANSWER.setOption(RANDOM_OPTION);
        RANDOM_ANSWER.setDeleted(false);
        RANDOM_ANSWER.setUser(RANDOM_USER);

        RANDOM_ANSWER_REQUEST = new SurveyAnswerRequest();
        RANDOM_ANSWER_REQUEST.setSurveyOptionId(RANDOM_OPTION.getId());

        when(service.getBubble(RANDOM_BUBBLE.getId())).thenReturn(RANDOM_BUBBLE);
        when(service.createBubble(RANDOM_EVENT.getRef())).thenReturn(RANDOM_BUBBLE);
        when(service.updateBubble(RANDOM_BUBBLE.getId(), RANDOM_BUBBLE_UPDATE_REQUEST)).thenReturn(RANDOM_BUBBLE);

        when(service.createOption(RANDOM_OPTION_REQUEST)).thenReturn(RANDOM_BUBBLE);
        when(service.updateOption(RANDOM_OPTION.getId(), RANDOM_OPTION_UPDATE_REQUEST)).thenReturn(RANDOM_BUBBLE);

        when(service.createAnswer(RANDOM_ANSWER_REQUEST)).thenReturn(RANDOM_BUBBLE);
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
                .andExpect(content().string(containsString(EXPECTED_CONTAIN)));}


    @Test
    public void testDeleteBubble() throws Exception {
        mockMvc.perform(delete(ROOT_URL + "/" + RANDOM_BUBBLE.getId()))
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

    @Test
    public void testCreateOption() throws Exception {
        String body = super.getJsonString(RANDOM_OPTION_REQUEST);

        mockMvc.perform(post(ROOT_URL + "/option").contentType(APPLICATION_JSON_UTF8)
                .content(body))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString(EXPECTED_CONTAIN)));
    }

    @Test
    public void testDeleteOption() throws Exception {
        mockMvc.perform(delete(ROOT_URL + "/option/" + RANDOM_OPTION.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testUpdateOption() throws Exception {
        String body = super.getJsonString(RANDOM_OPTION_UPDATE_REQUEST);

        mockMvc.perform(patch(ROOT_URL + "/option/" + RANDOM_OPTION.getId())
                .contentType(APPLICATION_JSON_UTF8).content(body))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(EXPECTED_CONTAIN)));
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
}
