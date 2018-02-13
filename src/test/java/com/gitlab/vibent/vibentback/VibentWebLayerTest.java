package com.gitlab.vibent.vibentback;

import com.gitlab.vibent.vibentback.user.User;
import com.gitlab.vibent.vibentback.user.UserService;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;


public class VibentWebLayerTest extends VibentTest {

    @Autowired
    public MockMvc mockMvc;

    @MockBean
    UserService userService;

    public void setUp(){
        when(userService.getUser(RANDOM_USER.getRef())).thenReturn(RANDOM_USER);
    }

}
