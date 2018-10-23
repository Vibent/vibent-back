package com.vibent.vibentback.bubble.planning;

import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.api.bubble.planning.PlanningBubbleRequest;
import com.vibent.vibentback.bubble.BubbleType;
import com.vibent.vibentback.bubble.planning.entry.PlanningEntry;
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
@WebMvcTest(value = PlanningController.class, secure = false)
public class PlanningWebLayerTest extends VibentTest {

    private final static String ROOT_URL = "/bubble/planning";
    /**
     * String used to check the response (i.e. response body must contain this string)
     */
    private final static String EXPECTED_CONTAIN = "\"entries\"";

    @Autowired
    public MockMvc mockMvc;

    @MockBean
    PlanningService service;

    PlanningBubble RANDOM_BUBBLE;
    PlanningEntry RANDOM_ENTRY;
    PlanningBubbleRequest RANDOM_REQ;

    @Before
    public void setUp() {
        super.setUp();
        RANDOM_BUBBLE = new PlanningBubble();
        RANDOM_ENTRY = new PlanningEntry();

        RANDOM_REQ = new PlanningBubbleRequest();
        RANDOM_REQ.setEventRef(RANDOM_EVENT.getRef());
        RANDOM_REQ.setTitle("Here's a title");

        RANDOM_BUBBLE.setId(666L);
        RANDOM_BUBBLE.setEntries(new HashSet<PlanningEntry>(){{
            add(RANDOM_ENTRY);
        }});
        RANDOM_BUBBLE.setEvent(RANDOM_EVENT);
        RANDOM_BUBBLE.setCreator(RANDOM_USER);
        RANDOM_BUBBLE.setDeleted(false);
        RANDOM_BUBBLE.setTitle("Title For Test");

        RANDOM_ENTRY.setId(123L);
        RANDOM_ENTRY.setBubble(RANDOM_BUBBLE);
        RANDOM_ENTRY.setDeleted(false);
        RANDOM_ENTRY.setContent("Entry Content For Test");
        RANDOM_ENTRY.setUser(RANDOM_USER);
        RANDOM_ENTRY.setStart(getFutureDate(4));
        RANDOM_ENTRY.setEnd(getFutureDate(5));

        when(service.getBubble(RANDOM_BUBBLE.getId())).thenReturn(RANDOM_BUBBLE);
        when(service.createBubble(RANDOM_REQ)).thenReturn(RANDOM_BUBBLE);}

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
}
