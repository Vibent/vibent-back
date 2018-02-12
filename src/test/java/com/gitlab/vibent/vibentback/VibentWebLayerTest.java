package com.gitlab.vibent.vibentback;

import com.gitlab.vibent.vibentback.user.User;
import com.gitlab.vibent.vibentback.user.UserService;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;

public class VibentWebLayerTest {

    @Autowired
    public MockMvc mockMvc;

    @MockBean
    UserService service;

    @Before
    public void setUp(){
        when(service.getUser("5")).thenReturn(new User("d","d","d","d","d","d"));
    }

}
