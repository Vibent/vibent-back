package com.vibent.vibentback.user;

import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.common.error.VibentError;
import com.vibent.vibentback.common.error.VibentException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.UUID;

@Slf4j
@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserDataTest extends VibentTest {

    @Autowired
    UserRepository userRepository;

    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void testAddUser() {
        RANDOM_USER = userRepository.save(RANDOM_USER);
        Assert.assertNotNull(RANDOM_USER.getRef());
    }

    @Test
    public void testGetUser() {
        Assume.assumeFalse(env.acceptsProfiles("gitlab-ci"));
        RANDOM_USER = userRepository.save(RANDOM_USER);
        log.info(RANDOM_USER.toString());
        User authUser = userRepository.findById(RANDOM_USER.getId())
                .orElseThrow(() -> new VibentException(VibentError.USER_NOT_FOUND));
        User user = userRepository.findByRef(RANDOM_USER.getRef())
                .orElseThrow(() -> new VibentException(VibentError.USER_NOT_FOUND));
        Assert.assertNotNull(authUser.getUsername());
        Assert.assertNotNull(user.getRef());
    }

    @Test
    public void testDeleteUser() {
        RANDOM_USER = userRepository.save(RANDOM_USER);

        userRepository.delete(RANDOM_USER);

        boolean found = userRepository.findById(RANDOM_USER.getId()).isPresent();
        Assert.assertFalse(found);
    }

    @Test
    public void testUpdateUser() {
        String randomFirstName = UUID.randomUUID().toString();
        String randomEmail = UUID.randomUUID().toString();
        RANDOM_USER = userRepository.save(RANDOM_USER);

        RANDOM_USER.setFirstName(randomFirstName);
        RANDOM_USER.setEmail(randomEmail);
        userRepository.save(RANDOM_USER);
        Assert.assertTrue(userRepository.findById(RANDOM_USER.getId()).isPresent());
        Assert.assertEquals(randomFirstName, userRepository.findById(RANDOM_USER.getId()).get().getFirstName());

        userRepository.save(RANDOM_USER);
        Assert.assertTrue(userRepository.findById(RANDOM_USER.getId()).isPresent());
        Assert.assertEquals(randomEmail, userRepository.findById(RANDOM_USER.getId()).get().getUsername());
    }
}
