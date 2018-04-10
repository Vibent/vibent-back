package com.vibent.vibentback.bubble.travel;

import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.api.travel.TravelBubbleRequest;
import com.vibent.vibentback.bubble.BubbleType;
import com.vibent.vibentback.bubble.travel.proposal.TravelProposal;
import com.vibent.vibentback.bubble.travel.request.TravelRequest;
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
@WebMvcTest(value = TravelController.class, secure = false)
public class TravelWebLayerTest extends VibentTest {

    private final static String ROOT_URL = "/bubble/travel";
    /**
     * String used to check the response (i.e. response body must contain this string)
     */
    private final static String EXPECTED_CONTAIN = "\"requests\"";

    @Autowired
    public MockMvc mockMvc;

    @MockBean
    TravelService service;

    TravelBubble RANDOM_BUBBLE;
    TravelProposal RANDOM_PROPOSAL;
    TravelRequest RANDOM_REQUEST;
    TravelBubbleRequest RANDOM_REQ;

    @Before
    public void setUp() {
        super.setUp();
        RANDOM_BUBBLE = new TravelBubble();
        RANDOM_PROPOSAL = new TravelProposal();
        RANDOM_REQUEST = new TravelRequest();


        RANDOM_REQ = new TravelBubbleRequest();
        RANDOM_REQ.setEventRef(RANDOM_EVENT.getRef());

        RANDOM_BUBBLE.setId(666L);
        RANDOM_BUBBLE.setType(BubbleType.TravelBubble);
        RANDOM_BUBBLE.setDeleted(false);
        RANDOM_BUBBLE.setEvent(RANDOM_EVENT);
        RANDOM_BUBBLE.setCreator(RANDOM_USER);
        RANDOM_BUBBLE.setProposals(new HashSet<TravelProposal>(){{
            add(RANDOM_PROPOSAL);
        }});


        RANDOM_PROPOSAL.setId(123L);
        RANDOM_PROPOSAL.setBubble(RANDOM_BUBBLE);
        RANDOM_PROPOSAL.setDeleted(false);
        RANDOM_PROPOSAL.setPassByCities("Cities For Tests");
        RANDOM_PROPOSAL.setUser(RANDOM_USER);
        RANDOM_PROPOSAL.setRequests(new HashSet<TravelRequest>(){{
            add(RANDOM_REQUEST);
        }});

        RANDOM_REQUEST.setId(546L);
        RANDOM_REQUEST.setBubble(RANDOM_BUBBLE);
        RANDOM_REQUEST.setDeleted(false);
        RANDOM_REQUEST.setProposal(RANDOM_PROPOSAL);
        RANDOM_REQUEST.setUser(RANDOM_USER);
        RANDOM_REQUEST.setCapacity(1);

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