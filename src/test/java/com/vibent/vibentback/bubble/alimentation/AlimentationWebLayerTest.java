package com.vibent.vibentback.bubble.alimentation;

import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.api.bubble.alimentation.AlimentationBubbleRequest;
import com.vibent.vibentback.bubble.BubbleType;
import com.vibent.vibentback.bubble.alimentation.bring.AlimentationBring;
import com.vibent.vibentback.bubble.alimentation.entry.AlimentationEntry;
import com.vibent.vibentback.user.UserService;
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
@WebMvcTest(value = AlimentationController.class, secure = false)
public class AlimentationWebLayerTest extends VibentTest {

    private final static String ROOT_URL = "/bubble/alimentation";
    /**
     * String used to check the response (i.e. response body must contain this string)
     */
    private final static String EXPECTED_CONTAIN = "\"brings\"";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AlimentationService alimentationService;
    @MockBean
    UserService userService;

    AlimentationBubble RANDOM_BUBBLE;
    AlimentationEntry RANDOM_ENTRY;
    AlimentationBring RANDOM_BRING;
    AlimentationBubbleRequest RANDOM_REQ;

    @Before
    public void setUp() {
        super.setUp();
        RANDOM_BUBBLE = new AlimentationBubble();
        RANDOM_ENTRY = new AlimentationEntry();
        RANDOM_BRING = new AlimentationBring();


        RANDOM_REQ = new AlimentationBubbleRequest();
        RANDOM_REQ.setEventRef(RANDOM_EVENT.getRef());

        RANDOM_BUBBLE.setId(666L);
        RANDOM_BUBBLE.setDeleted(false);
        RANDOM_BUBBLE.setEvent(RANDOM_EVENT);
        RANDOM_BUBBLE.setCreator(RANDOM_USER);
        RANDOM_BUBBLE.setEntries(new HashSet<AlimentationEntry>() {{
            add(RANDOM_ENTRY);
        }});


        RANDOM_ENTRY.setId(123L);
        RANDOM_ENTRY.setBubble(RANDOM_BUBBLE);
        RANDOM_ENTRY.setDeleted(false);
        RANDOM_ENTRY.setName("Coke");
        RANDOM_ENTRY.setType(AlimentationEntry.Type.DRINK);
        RANDOM_ENTRY.setTotalRequested(10);
        RANDOM_ENTRY.setBrings(new HashSet<AlimentationBring>() {{
            add(RANDOM_BRING);
        }});

        RANDOM_BRING.setId(546L);
        RANDOM_BRING.setEntry(RANDOM_ENTRY);
        RANDOM_BRING.setDeleted(false);
        RANDOM_BRING.setQuantity(5);
        RANDOM_BRING.setUser(RANDOM_USER);

        // when(userService.getUserByUsername(RANDOM_USER.getUsername())).thenReturn(RANDOM_USER);
        when(alimentationService.getBubble(RANDOM_BUBBLE.getId())).thenReturn(RANDOM_BUBBLE);
        when(alimentationService.createBubble(RANDOM_EVENT.getRef())).thenReturn(RANDOM_BUBBLE);
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
        String body = super.getJsonString(RANDOM_REQ);

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

}