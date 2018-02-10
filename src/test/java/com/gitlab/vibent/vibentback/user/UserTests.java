package com.gitlab.vibent.vibentback.user;

import com.gitlab.vibent.vibentback.VibentTests;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTests extends VibentTests {

    @Autowired
    UserRepository repository;

    @Test
    public void testAddUser(){
        User user = new User(UUID.randomUUID().toString(),"conor","ryan","cr.sd.sd@gmail.com","secret","sel");
        repository.save(user);
    }

}
