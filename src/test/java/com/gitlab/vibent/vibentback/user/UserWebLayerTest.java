package com.gitlab.vibent.vibentback.user;

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
@WebMvcTest(UserController.class)
public class UserWebLayerTest extends VibentTest {

    @Autowired
    public MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Before
    public void setUp(){
        when(userService.getUser(RANDOM_USER.getRef())).thenReturn(RANDOM_USER);
        when(userService.addUser(RANDOM_USER)).thenReturn(RANDOM_USER);
    }

    @Test
    public void testGetUser() throws Exception {
        this.mockMvc.perform(get("/user/" + RANDOM_USER.getRef())).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("firstName")));
    }

    @Test
    public void testAddUser() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(RANDOM_USER);

        mockMvc.perform(post("/user").contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("firstName")));
    }

    @Test
    public void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/user/" + RANDOM_USER.getRef()))
                .andExpect(status().isNoContent());
    }
}