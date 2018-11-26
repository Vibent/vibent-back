package com.vibent.vibentback.bubble.survey;

import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.api.bubble.survey.SurveyBubbleRequest;
import com.vibent.vibentback.bubble.BubbleType;
import com.vibent.vibentback.bubble.survey.SurveyBubble;
import com.vibent.vibentback.bubble.survey.SurveyController;
import com.vibent.vibentback.bubble.survey.SurveyService;
import com.vibent.vibentback.bubble.survey.answer.SurveyAnswer;
import com.vibent.vibentback.bubble.survey.option.SurveyOption;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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

    @Before
    public void setUp() {
        super.setUp();
        RANDOM_BUBBLE = new SurveyBubble();
        RANDOM_OPTION = new SurveyOption();
        RANDOM_ANSWER = new SurveyAnswer();

        RANDOM_BUBBLE_REQUEST = new SurveyBubbleRequest();
        RANDOM_BUBBLE_REQUEST.setEventRef(RANDOM_EVENT.getRef());
        RANDOM_BUBBLE_REQUEST.setTitle("Survey Title");

        RANDOM_BUBBLE.setId(666L);
        RANDOM_BUBBLE.setOptions(new HashSet<SurveyOption>(){{
            add(RANDOM_OPTION);
        }});
        RANDOM_BUBBLE.setEvent(RANDOM_EVENT);
        RANDOM_BUBBLE.setCreator(RANDOM_USER);
        RANDOM_BUBBLE.setDeleted(false);

        RANDOM_OPTION.setId(123L);
        RANDOM_OPTION.setBubble(RANDOM_BUBBLE);
        RANDOM_OPTION.setDeleted(false);
        RANDOM_OPTION.setContent("Response Content For Test");
        RANDOM_OPTION.setUser(RANDOM_USER);
        RANDOM_OPTION.setAnswers(new HashSet<SurveyAnswer>(){{
            add(RANDOM_ANSWER);
        }});

        RANDOM_ANSWER.setId(546L);
        RANDOM_ANSWER.setOption(RANDOM_OPTION);
        RANDOM_ANSWER.setDeleted(false);
        RANDOM_ANSWER.setUser(RANDOM_USER);

        when(service.getBubble(RANDOM_BUBBLE.getId())).thenReturn(RANDOM_BUBBLE);
        when(service.createBubble(RANDOM_BUBBLE_REQUEST)).thenReturn(RANDOM_BUBBLE);}

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

}
