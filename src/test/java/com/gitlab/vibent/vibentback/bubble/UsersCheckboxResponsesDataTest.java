package com.gitlab.vibent.vibentback.bubble;

import com.gitlab.vibent.vibentback.VibentTest;
import com.gitlab.vibent.vibentback.bubble.checkbox.*;
import com.gitlab.vibent.vibentback.user.User;
import com.gitlab.vibent.vibentback.user.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UsersCheckboxResponsesDataTest extends VibentTest {

    @Autowired
    UsersCheckboxResponsesRepository repository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CheckboxResponseRepository checkboxResponseRepository;

    @Autowired
    CheckboxBubbleRepository checkboxBubbleRepository;

    User user;
    CheckboxResponse checkboxResponse;
    CheckboxBubble checkboxBubble;

    @Before
    public void init()
    {
        /** User creation **/
        user = new User(UUID.randomUUID().toString(),"conor","ryan","cr.sd.sd@gmail.com","secret","sel");
        userRepository.save(user);
        /** Bubble creation **/
        checkboxBubble = new CheckboxBubble("checkboxBubbleTitle");
        checkboxBubbleRepository.save(checkboxBubble);
        /** Bubble response creation **/
        checkboxResponse = new CheckboxResponse(checkboxBubble.getId(), "response");
        checkboxResponseRepository.save(checkboxResponse);
    }

    @Test
    public void testAddCheckboxUserResponse(){
        UsersCheckboxResponses usersCheckboxResponses = new UsersCheckboxResponses(user.getRef(), checkboxResponse.getId());
        repository.save(usersCheckboxResponses);
    }
}
