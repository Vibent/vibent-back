package com.gitlab.vibent.vibentback.user;

import com.gitlab.vibent.vibentback.VibentTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserDataTest extends VibentTest {

    @Autowired
    UserRepository repository;

    @Before
    public void setUp() {
        repository.save(RANDOM_USER);
    }

    @After
    public void tearDown() {
        repository.deleteByRef(RANDOM_USER.getRef());
    }

    @Test
    public void testAddUser() {
        User user = new User(UUID.randomUUID().toString(), "first", "last", "bang@email.com", "secret", "salty");
        user = repository.save(user);
        Assert.assertNotNull(user.getRef());

        // Clean up
        repository.delete(user);
    }

    @Test
    public void testGetUser() {
        User user = repository.findByRef(RANDOM_USER.getRef());
        Assert.assertNotNull(user.getRef());
    }

    @Test
    public void testDeleteUser() {
        Integer deletedAmount = repository.deleteByRef(RANDOM_USER.getRef());
        Assert.assertEquals(1, deletedAmount.intValue());
    }

}
