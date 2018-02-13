package com.gitlab.vibent.vibentback.user;

import com.gitlab.vibent.vibentback.VibentDataTest;
import com.gitlab.vibent.vibentback.VibentInternalTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.util.UUID;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserDataTest extends VibentDataTest {

    @Autowired
    UserRepository repository;

    @Before
    public void setUp(){
        repository.save(RANDOM_USER);
    }

    @After
    public void tearDown(){
        repository.deleteByRef(RANDOM_USER.getRef());
    }

    @Test
    public void testAddUser(){
        User user = new User(UUID.randomUUID().toString(),"first","last","bang@email.com","secret","salty");
        user = repository.save(user);
        Assert.notNull(user.getRef(), "Returned user has null property");
    }

    @Test
    public void testGetUser(){
        User user = repository.findByRef(RANDOM_USER.getRef());
        Assert.notNull(user.getRef(), "Returned user has null property");
    }

}
