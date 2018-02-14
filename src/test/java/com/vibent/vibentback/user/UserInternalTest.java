package com.vibent.vibentback.user;

import com.vibent.vibentback.VibentTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.jws.soap.SOAPBinding;

import static org.mockito.Mockito.when;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserInternalTest extends VibentTest {

    @Autowired
    @InjectMocks
    private UserController controller;

    @MockBean
    private UserRepository userRepository;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        when(userRepository.findByRef(RANDOM_USER.getRef())).thenReturn(RANDOM_USER);
        when(userRepository.save(RANDOM_USER)).thenReturn(RANDOM_USER);
        when(userRepository.deleteByRef(RANDOM_USER.getRef())).thenReturn(1);
    }

    @Test
    public void getUser(){
        User user = controller.getUser(RANDOM_USER.getRef());
        Assert.assertEquals(RANDOM_USER.getRef(), user.getRef());
    }

    @Test
    public void addUser(){
        User user = controller.createUser(RANDOM_USER);
        Assert.assertEquals(RANDOM_USER.getRef(), user.getRef());
    }

    @Test
    public void deleteUser(){
        controller.deleteUser(RANDOM_USER.getRef());
    }

    @Test
    public void updateUser(){
        User user = RANDOM_USER;
        user.setFirstName("John");
        controller.updateUser(RANDOM_USER.getRef(), user);
        Assert.assertEquals(user, RANDOM_USER);
    }
}
