package com.vibent.vibentback.event;

import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.event.api.EventRequest;
import com.vibent.vibentback.event.api.EventUpdateRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = EventController.class, secure = false)
public class EventWebLayerTest extends VibentTest {

    @Autowired
    public MockMvc mockMvc;

    @MockBean
    EventService eventService;

    private EventRequest RANDOM_EVENT_REQUEST;
    private EventUpdateRequest RANDOM_EVENT_UPDATE_REQUEST;

    @Before
    public void setUp(){
        super.setUp();

        RANDOM_EVENT_REQUEST = new EventRequest();
        RANDOM_EVENT_REQUEST.setTitle("Random title");
        RANDOM_EVENT_REQUEST.setDescription("Random descipt.");
        RANDOM_EVENT_REQUEST.setStartDate(getFutureDate(5));
        RANDOM_EVENT_REQUEST.setStartDate(getFutureDate(10));
        RANDOM_EVENT_REQUEST.setGroupRef(RANDOM_GROUP.getRef());

        RANDOM_EVENT_UPDATE_REQUEST = new EventUpdateRequest();
        RANDOM_EVENT_UPDATE_REQUEST.setDescription("New description");

        when(eventService.getEvent(RANDOM_EVENT.getRef())).thenReturn(RANDOM_EVENT);
        when(eventService.createEvent(RANDOM_EVENT_REQUEST)).thenReturn(RANDOM_EVENT);
        when(eventService.updateEvent(RANDOM_EVENT.getRef(), RANDOM_EVENT_UPDATE_REQUEST)).thenReturn(RANDOM_EVENT);
    }

    @Test
    public void testGetEvent() throws Exception {
        this.mockMvc.perform(get("/event/" + RANDOM_EVENT.getRef())).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("title")));
    }

    @Test
    public void testAddEvent() throws Exception {
        String body = super.getJsonString(RANDOM_EVENT_REQUEST);

        mockMvc.perform(post("/event").contentType(APPLICATION_JSON_UTF8)
                .content(body))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("title")));
    }

    @Test
    public void testUpdateEvent() throws Exception {
        String body = super.getJsonString(RANDOM_EVENT_UPDATE_REQUEST);

        mockMvc.perform(patch("/event/" + RANDOM_EVENT.getRef()).contentType(APPLICATION_JSON_UTF8)
                .content(body))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("title")));
    }

    @Test
    public void testDeleteEvent() throws Exception {
        mockMvc.perform(delete("/event/" + RANDOM_EVENT.getRef()))
                .andExpect(status().isNoContent());
    }
}