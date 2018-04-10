package com.vibent.vibentback.user;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class, secure = false)
public class UserWebLayerTest extends VibentTest {

    @Autowired
    public MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Before
    public void setUp() {
        super.setUp();
        when(userService.getUserByRef(RANDOM_USER.getRef())).thenReturn(RANDOM_USER);
        when(userService.addUser(RANDOM_USER)).thenReturn(RANDOM_USER);
    }

    @Test
    public void testGetUser() throws Exception {
        this.mockMvc.perform(get("/user/" + RANDOM_USER.getRef())).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("firstName")));
    }
}