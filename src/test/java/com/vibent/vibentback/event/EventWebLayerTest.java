package com.vibent.vibentback.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.vibent.vibentback.VibentTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(EventController.class)
public class EventWebLayerTest extends VibentTest {

    @Autowired
    public MockMvc mockMvc;

    @MockBean
    EventService eventService;

    @Before
    public void setUp(){
        super.setUp();
        when(eventService.getEvent(RANDOM_EVENT.getRef())).thenReturn(RANDOM_EVENT);
        when(eventService.addEvent(RANDOM_EVENT)).thenReturn(RANDOM_EVENT);
    }

    @Test
    public void testGetEvent() throws Exception {
        this.mockMvc.perform(get("/event/" + RANDOM_EVENT.getRef())).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("title")));
    }

    @Test
    public void testAddEvent() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(RANDOM_EVENT);

        mockMvc.perform(post("/event").contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("title")));
    }

    @Test
    public void testDeleteEvent() throws Exception {
        mockMvc.perform(delete("/event/" + RANDOM_EVENT.getRef()))
                .andExpect(status().isNoContent());
    }
}