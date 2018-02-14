package com.gitlab.vibent.vibentback.groupT;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.gitlab.vibent.vibentback.VibentTest;
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
@WebMvcTest(GroupTController.class)
public class GroupTWebLayerTest extends VibentTest {

    @Autowired
    public MockMvc mockMvc;

    @MockBean
    GroupTService groupTService;

    @Before
    public void setUp(){
        when(groupTService.getGroupT(RANDOM_GROUP.getRef())).thenReturn(RANDOM_GROUP);
        when(groupTService.addGroupT(RANDOM_GROUP)).thenReturn(RANDOM_GROUP);
    }

    @Test
    public void testGetGroupT() throws Exception {
        this.mockMvc.perform(get("/group/" + RANDOM_GROUP.getRef())).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("name")));
    }

    @Test
    public void testAddGroupT() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(RANDOM_GROUP);

        mockMvc.perform(post("/group").contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("name")));
    }

    @Test
    public void testDeleteGroupT() throws Exception {
        mockMvc.perform(delete("/group/" + RANDOM_GROUP.getRef()))
                .andExpect(status().isNoContent());
    }
}