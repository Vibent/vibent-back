package com.vibent.vibentback.user;

import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.common.ObjectUpdater;
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
        User user = new User(UUID.randomUUID().toString(), "test", "test", "test@test.com", "test", "test");
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

    @Test
    public void testUpdateUser() {
        // Set up
        User newUser = new User();
        newUser.setRef("newRefShouldNotUpdate");
        newUser.setId(-1L);
        newUser.setFirstName("NewFirstNameShouldUpdate");
        User old = repository.findByRef(RANDOM_USER.getRef());
        String oldLastNameShouldNotUpdate = old.getLastName();

        // To test
        ObjectUpdater.updateProperties(newUser, old);

        User checkUser = repository.save(old);
        Assert.assertEquals("NewFirstNameShouldUpdate", checkUser.getFirstName());
        Assert.assertNotEquals(-1L, checkUser.getId().longValue());
        Assert.assertNotEquals("newRefShouldNotUpdate", checkUser.getRef());
        Assert.assertEquals(oldLastNameShouldNotUpdate, checkUser.getLastName());
    }
}
