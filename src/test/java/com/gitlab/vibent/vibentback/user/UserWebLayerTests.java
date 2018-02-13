package com.gitlab.vibent.vibentback.user;

// https://spring.io/guides/gs/testing-web/

import com.gitlab.vibent.vibentback.VibentWebLayerTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserWebLayerTests extends VibentWebLayerTest {


    @Before
    public void setUp(){
        super.setUp();
    }

    @Test
    public void greetingShouldReturnMessageFromService() throws Exception {
        this.mockMvc.perform(get("/user/5")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("firstName")));
    }
}