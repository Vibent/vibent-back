package com.vibent.vibentback.bubble.alimentation;

import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.api.bubble.alimentation.*;
import com.vibent.vibentback.bubble.alimentation.bring.AlimentationBring;
import com.vibent.vibentback.bubble.alimentation.entry.AlimentationEntry;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

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
    AlimentationBubbleRes RANDOM_RESPONSE;
    AlimentationBubbleReq RANDOM_BUBBLE_REQUEST;
    AlimentationEntryReq RANDOM_ENTRY_REQUEST;
    AlimentationEntryUpdateReq RANDOM_ENTRY_UPDATE_REQUEST;
    AlimentationBringReq RANDOM_BRING_REQUEST;
    AlimentationBringUpdateReq RANDOM_BRING_UPDATE_REQUEST;

    @Before
    public void setUp() {
        super.setUp();
        RANDOM_BUBBLE = new AlimentationBubble();
        RANDOM_BUBBLE.setId(666L);

        RANDOM_ENTRY = new AlimentationEntry();
        RANDOM_ENTRY.setId(123L);
        RANDOM_ENTRY.setBubbleId(RANDOM_BUBBLE.getId());
        RANDOM_ENTRY.setDeleted(false);
        RANDOM_ENTRY.setName("Coke");
        RANDOM_ENTRY.setType(AlimentationEntry.Type.Drink);
        RANDOM_ENTRY.setTotalCurrent(5);
        RANDOM_ENTRY.setTotalRequested(10);

        RANDOM_BRING = new AlimentationBring();
        RANDOM_BRING.setId(546L);
        RANDOM_BRING.setEntryId(RANDOM_ENTRY.getId());
        RANDOM_BRING.setDeleted(false);
        RANDOM_BRING.setQuantity(5);
        RANDOM_BRING.setUserRef(RANDOM_USER.getRef());

        RANDOM_RESPONSE = new AlimentationBubbleRes(RANDOM_BUBBLE);
        RANDOM_RESPONSE.addEntry(RANDOM_ENTRY, Collections.singletonList(RANDOM_BRING));

        RANDOM_BUBBLE_REQUEST = new AlimentationBubbleReq();
        RANDOM_BUBBLE_REQUEST.setEventRef(RANDOM_EVENT.getRef());

        RANDOM_ENTRY_REQUEST = new AlimentationEntryReq();
        RANDOM_ENTRY_REQUEST.setBubbleId(RANDOM_BUBBLE.getId());
        RANDOM_ENTRY_REQUEST.setName("Frites");
        RANDOM_ENTRY_REQUEST.setTotalCurrent(0);
        RANDOM_ENTRY_REQUEST.setTotalRequested(10);
        RANDOM_ENTRY_REQUEST.setType(AlimentationEntry.Type.Food);

        RANDOM_ENTRY_UPDATE_REQUEST = new AlimentationEntryUpdateReq();
        RANDOM_ENTRY_UPDATE_REQUEST.setTotalRequested(15);

        RANDOM_BRING_REQUEST = new AlimentationBringReq();
        RANDOM_BRING_REQUEST.setEntryId(RANDOM_ENTRY.getId());
        RANDOM_BRING_REQUEST.setQuantity(10);

        RANDOM_BRING_UPDATE_REQUEST = new AlimentationBringUpdateReq();
        RANDOM_BRING_UPDATE_REQUEST.setQuantity(15);

        when(service.getBubble(RANDOM_BUBBLE.getId())).thenReturn(RANDOM_RESPONSE);
        when(service.createBubble(RANDOM_BUBBLE_REQUEST)).thenReturn(RANDOM_RESPONSE);
        when(service.updateBubble(RANDOM_BUBBLE.getId(), RANDOM_BUBBLE)).thenReturn(RANDOM_RESPONSE);
        when(service.createBubbleEntry(RANDOM_ENTRY_REQUEST)).thenReturn(RANDOM_RESPONSE);
        when(service.updateBubbleEntry(RANDOM_ENTRY.getId(), RANDOM_ENTRY_UPDATE_REQUEST)).thenReturn(RANDOM_RESPONSE);
        when(service.createBubbleBring(RANDOM_BRING_REQUEST)).thenReturn(RANDOM_RESPONSE);
        when(service.updateBubbleBring(RANDOM_BRING.getId(), RANDOM_BRING_UPDATE_REQUEST)).thenReturn(RANDOM_RESPONSE);
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
    public void testCreateEntry() throws Exception {
        String body = super.getJsonString(RANDOM_ENTRY_REQUEST);

        mockMvc.perform(post(ROOT_URL + "/entry").contentType(APPLICATION_JSON_UTF8)
                .content(body))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString(EXPECTED_CONTAIN)));
    }

    @Test
    public void testDeleteEntry() throws Exception {
        mockMvc.perform(delete(ROOT_URL + "/entry/" + RANDOM_ENTRY.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testUpdateEntry() throws Exception {
        String body = super.getJsonString(RANDOM_ENTRY_UPDATE_REQUEST);

        mockMvc.perform(patch(ROOT_URL + "/entry/" + RANDOM_ENTRY.getId())
                .contentType(APPLICATION_JSON_UTF8).content(body))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(EXPECTED_CONTAIN)));
    }

    @Test
    public void testCreateBring() throws Exception {
        String body = super.getJsonString(RANDOM_BRING_REQUEST);

        mockMvc.perform(post(ROOT_URL + "/bring").contentType(APPLICATION_JSON_UTF8)
                .content(body))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString(EXPECTED_CONTAIN)));
    }

    @Test
    public void testDeleteBring() throws Exception {
        mockMvc.perform(delete(ROOT_URL + "/bring/" + RANDOM_BRING.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testUpdateBring() throws Exception {
        String body = super.getJsonString(RANDOM_BRING_UPDATE_REQUEST);

        mockMvc.perform(patch(ROOT_URL + "/bring/" + RANDOM_BRING.getId())
                .contentType(APPLICATION_JSON_UTF8).content(body))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(EXPECTED_CONTAIN)));
    }
}