package com.vibent.vibentback.common;

import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.error.VibentError;
import com.vibent.vibentback.error.VibentException;
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

import javax.transaction.Transactional;

@Slf4j
@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class DeletingAndRecoverTest extends VibentTest {

    @Autowired
    UserRepository repository;

    @Before
    public void setUp(){
        super.setUp();
    }

    @Test
    public void deleteTest(){
        RANDOM_USER = repository.save(RANDOM_USER);
        User user = repository.findById(RANDOM_USER.getId())
                .orElseThrow(() -> new VibentException(VibentError.USER_NOT_FOUND));
        Assert.assertNotNull(repository.findById(user.getId()));
        repository.delete(user);
        Assert.assertFalse(repository.findById(user.getId()).isPresent());
    }

    @Test
    public void recoverTest(){
        RANDOM_USER = repository.save(RANDOM_USER);
        repository.delete(RANDOM_USER);
        Assert.assertFalse(repository.findById(RANDOM_USER.getId()).isPresent());

        Assert.assertEquals(1, repository.recover(RANDOM_USER.getRef()));
        Assert.assertNotNull(repository.findById(RANDOM_USER.getId()));
    }

    @Test
    public void isDeletedTest(){
        RANDOM_USER = repository.save(RANDOM_USER);
        Assert.assertFalse(repository.isDeleted(RANDOM_USER.getRef()));
        repository.delete(RANDOM_USER);
        Assert.assertTrue(repository.isDeleted(RANDOM_USER.getRef()));
    }
}
