package com.vibent.vibentback.common;

import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.user.User;
import com.vibent.vibentback.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class DeletingAndRecoverTests extends VibentTest {

    @Autowired
    UserRepository repository;

    @Before
    public void setUp(){
        super.setUp();
    }

    @Test
    public void deleteTest(){
        User user = repository.save(RANDOM_USER);
        user = repository.findByRef(user.getRef());
        Assert.assertNotNull(repository.findByRef(user.getRef()));
        repository.deleteByRef(user.getRef());
        Assert.assertNull(repository.findByRef(user.getRef()));
    }

    @Test
    public void recoverTest(){
        User user = repository.save(RANDOM_USER);
        repository.deleteByRef(user.getRef());
        Assert.assertNull(repository.findByRef(user.getRef()));

        Assert.assertEquals(1, repository.recover(user.getRef()));
        Assert.assertNotNull(repository.findByRef(user.getRef()));
    }

    @Test
    public void isDeletedTest(){
        User user = repository.save(RANDOM_USER);
        Assert.assertFalse(repository.isDeleted(user.getRef()));
        repository.deleteByRef(user.getRef());
        Assert.assertTrue(repository.isDeleted(user.getRef()));
    }
}
