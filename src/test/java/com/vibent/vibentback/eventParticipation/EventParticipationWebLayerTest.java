package com.vibent.vibentback.eventParticipation;

import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.api.eventParticipation.UpdateEventParticipationRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = EventParticipationController.class, secure = false)
public class EventParticipationWebLayerTest extends VibentTest {

    private final static String EXPECTED_CONTAIN_USER = "\"userRef\"";
    private final static String EXPECTED_CONTAIN_EVENT = "\"eventRef\"";

    @Autowired
    public MockMvc mockMvc;

    @MockBean
    EventParticipationService eventParticipationService;

    private EventParticipation RANDOM_EVENT_PARTICIPATION;
    private UpdateEventParticipationRequest RANDOM_EVENT_PARTICIPATION_UPDATE_REQUEST;

    @Before
    public void setUp(){
        super.setUp();

        RANDOM_EVENT_PARTICIPATION = new EventParticipation();
        RANDOM_EVENT_PARTICIPATION.setAnswer(EventParticipation.Answer.YES);
        RANDOM_EVENT_PARTICIPATION.setEvent(RANDOM_EVENT);
        RANDOM_EVENT_PARTICIPATION.setId(666L);
        RANDOM_EVENT_PARTICIPATION.setUser(RANDOM_USER);
        RANDOM_EVENT_PARTICIPATION.setVisible(true);

        RANDOM_EVENT_PARTICIPATION_UPDATE_REQUEST = new UpdateEventParticipationRequest();
        RANDOM_EVENT_PARTICIPATION_UPDATE_REQUEST.setAnswer(EventParticipation.Answer.NO);
        RANDOM_EVENT_PARTICIPATION_UPDATE_REQUEST.setIsVisible(false);

        Set<EventParticipation> participations = new HashSet<>(Arrays.asList(RANDOM_EVENT_PARTICIPATION, RANDOM_EVENT_PARTICIPATION));

        when(eventParticipationService.getUsersEventParticipations(RANDOM_USER.getRef()))
                .thenReturn(participations);
        when(eventParticipationService.getEventParticipations(RANDOM_EVENT.getRef()))
                .thenReturn(participations);
        when(eventParticipationService.updateEventParticipation(RANDOM_EVENT_PARTICIPATION.getId(), RANDOM_EVENT_PARTICIPATION_UPDATE_REQUEST))
                .thenReturn(RANDOM_EVENT_PARTICIPATION);
    }

    @Test
    public void getUsersEventParticipations() throws Exception {
        this.mockMvc.perform(get("/participation/user/" + RANDOM_USER.getRef())).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(EXPECTED_CONTAIN_USER)))
                .andExpect(content().string(containsString(EXPECTED_CONTAIN_EVENT)));
    }

    @Test
    public void getEventParticipations() throws Exception {
        this.mockMvc.perform(get("/participation/event/" + RANDOM_EVENT.getRef())).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(EXPECTED_CONTAIN_USER)))
                .andExpect(content().string(containsString(EXPECTED_CONTAIN_EVENT)));
    }

    @Test
    public void updateEventParticipation() throws Exception {
        String body = super.getJsonString(RANDOM_EVENT_PARTICIPATION_UPDATE_REQUEST);

        this.mockMvc.perform(patch("/participation/" + RANDOM_EVENT_PARTICIPATION.getId())
                .contentType(APPLICATION_JSON_UTF8)
                .content(body))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(EXPECTED_CONTAIN_USER)))
                .andExpect(content().string(containsString(EXPECTED_CONTAIN_EVENT)));
    }

}