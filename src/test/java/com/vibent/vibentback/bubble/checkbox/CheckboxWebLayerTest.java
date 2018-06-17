package com.vibent.vibentback.bubble.checkbox;

import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.api.bubble.checkbox.CheckboxBubbleRequest;
import com.vibent.vibentback.bubble.BubbleType;
import com.vibent.vibentback.bubble.checkbox.option.CheckboxOption;
import com.vibent.vibentback.bubble.checkbox.answer.CheckboxAnswer;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Slf4j
@RunWith(SpringRunner.class)
@WebMvcTest(value = CheckboxController.class, secure = false)
public class CheckboxWebLayerTest extends VibentTest {

    private final static String ROOT_URL = "/bubble/checkbox";
    /**
     * String used to check the response (i.e. response body must contain this string)
     */
    private final static String EXPECTED_CONTAIN = "\"answers\"";

    @Autowired
    public MockMvc mockMvc;

    @MockBean
    CheckboxService service;

    CheckboxBubble RANDOM_BUBBLE;
    CheckboxOption RANDOM_OPTION;
    CheckboxAnswer RANDOM_ANSWER;
    CheckboxBubbleRequest RANDOM_BUBBLE_REQUEST;

    @Before
    public void setUp() {
        super.setUp();
        RANDOM_BUBBLE = new CheckboxBubble();
        RANDOM_OPTION = new CheckboxOption();
        RANDOM_ANSWER = new CheckboxAnswer();

        RANDOM_BUBBLE_REQUEST = new CheckboxBubbleRequest();
        RANDOM_BUBBLE_REQUEST.setEventRef(RANDOM_EVENT.getRef());
        RANDOM_BUBBLE_REQUEST.setTitle("Checkbox Title");

        RANDOM_BUBBLE.setId(666L);
        RANDOM_BUBBLE.setOptions(new HashSet<CheckboxOption>(){{
            add(RANDOM_OPTION);
        }});
        RANDOM_BUBBLE.setType(BubbleType.CheckboxBubble);
        RANDOM_BUBBLE.setEvent(RANDOM_EVENT);
        RANDOM_BUBBLE.setCreator(RANDOM_USER);
        RANDOM_BUBBLE.setDeleted(false);

        RANDOM_OPTION.setId(123L);
        RANDOM_OPTION.setBubble(RANDOM_BUBBLE);
        RANDOM_OPTION.setDeleted(false);
        RANDOM_OPTION.setContent("Response Content For Test");
        RANDOM_OPTION.setUser(RANDOM_USER);
        RANDOM_OPTION.setAnswers(new HashSet<CheckboxAnswer>(){{
            add(RANDOM_ANSWER);
        }});

        RANDOM_ANSWER.setId(546L);
        RANDOM_ANSWER.setOption(RANDOM_OPTION);
        RANDOM_ANSWER.setDeleted(false);
        RANDOM_ANSWER.setUser(RANDOM_USER);

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
