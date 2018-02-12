package com.gitlab.vibent.vibentback.user;

import com.gitlab.vibent.vibentback.VibentTests;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserTests extends VibentTests {

    @Autowired
    UserRepository repository;

    private String ref;

    @Before
    public void setUp(){
        User user = new User(UUID.randomUUID().toString(),"conor","ryan","cr.sd.sd@gmail.com","secret","sel");
        ref = user.getRef();
        repository.save(user);
    }

    @Test
    public void testAddUser(){
        User user = new User(UUID.randomUUID().toString(),"conor","ryan","cr.sd.sd@gmail.com","secret","sel");
        log.info(repository.save(user).toString());

    }

    @Test
    public void testGetUser(){
        log.info(ref);
        Iterable<User> users = repository.findAll();
        users.forEach(e -> log.info("we have a user ! " + e.toString()));
        User u = repository.findByRef(ref);
        log.info(u.toString());
    }
}
