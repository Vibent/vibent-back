package com.vibent.vibentback.bubble.free;

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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(FreeBubbleController.class)
public class FreeBubbleWebLayerTest extends VibentTest {

    @Autowired
    public MockMvc mockMvc;

    @MockBean
    FreeBubbleService freeBubbleService;

    @Before
    public void setUp() {
        when(freeBubbleService.getBubble(RAMDOM_FREE_BUBBLE.getId())).thenReturn(RAMDOM_FREE_BUBBLE);
        when(freeBubbleService.addBubble(RAMDOM_FREE_BUBBLE)).thenReturn(RAMDOM_FREE_BUBBLE);
    }

    @Test
    public void testGetBubble() throws Exception {
        this.mockMvc.perform(get("/bubble/" + RAMDOM_FREE_BUBBLE.getId())).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("title")));
    }

    @Test
    public void testAddBubble() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(RAMDOM_FREE_BUBBLE);

        mockMvc.perform(post("/bubble").contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("title")));
    }

    @Test
    public void testDeleteBubble() throws Exception {
        mockMvc.perform(delete("/bubble/" + RAMDOM_FREE_BUBBLE.getId()))
                .andExpect(status().isNoContent());
    }
}