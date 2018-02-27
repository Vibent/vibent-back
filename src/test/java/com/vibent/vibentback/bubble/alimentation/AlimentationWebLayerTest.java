package com.vibent.vibentback.bubble.alimentation;

import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.bubble.alimentation.bring.AlimentationBring;
import com.vibent.vibentback.bubble.alimentation.entry.AlimentationEntry;
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
@WebMvcTest(AlimentationController.class)
public class AlimentationWebLayerTest extends VibentTest {

    private final static String ROOT_URL = "/bubble/alimentation";
    /**
     * String used to check the response (i.e. response body must contain this string)
     */
    private final static String EXPECTED_CONTAIN = "\"brings\"";

    @Autowired
    public MockMvc mockMvc;

    @MockBean
    AlimentationService service;

    AlimentationBubble RANDOM_BUBBLE;
    AlimentationEntry RANDOM_ENTRY;
    AlimentationBring RANDOM_BRING;

    @Before
    public void setUp() {
        super.setUp();
        RANDOM_BUBBLE = new AlimentationBubble();
        RANDOM_ENTRY = new AlimentationEntry();
        RANDOM_BRING = new AlimentationBring();

        RANDOM_BUBBLE.setId(666L);
        RANDOM_BUBBLE.setEntries(new HashSet<AlimentationEntry>(){{
            add(RANDOM_ENTRY);
        }});


        RANDOM_ENTRY.setId(123L);
        RANDOM_ENTRY.setBubble(RANDOM_BUBBLE);
        RANDOM_ENTRY.setDeleted(false);
        RANDOM_ENTRY.setName("Coke");
        RANDOM_ENTRY.setType(AlimentationEntry.Type.Drink);
        RANDOM_ENTRY.setTotalCurrent(5);
        RANDOM_ENTRY.setTotalRequested(10);
        RANDOM_ENTRY.setBrings(new HashSet<AlimentationBring>(){{
            add(RANDOM_BRING);
        }});

        RANDOM_BRING.setId(546L);
        RANDOM_BRING.setEntry(RANDOM_ENTRY);
        RANDOM_BRING.setDeleted(false);
        RANDOM_BRING.setQuantity(5);
        RANDOM_BRING.setUser(RANDOM_USER);

        when(service.getBubble(RANDOM_BUBBLE.getId())).thenReturn(RANDOM_BUBBLE);
        when(service.createBubble(RANDOM_EVENT.getRef())).thenReturn(RANDOM_BUBBLE);
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
        JSONObject object = new JSONObject();
        object.put("eventRef", RANDOM_EVENT.getRef());
        String body = super.getJsonString(RANDOM_EVENT.getRef());

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