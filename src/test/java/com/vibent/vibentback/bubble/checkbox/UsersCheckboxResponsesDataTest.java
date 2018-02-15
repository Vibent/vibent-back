package com.vibent.vibentback.bubble.checkbox;

import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.bubble.checkbox.CheckboxBubble;
import com.vibent.vibentback.bubble.checkbox.CheckboxBubbleRepository;
import com.vibent.vibentback.bubble.checkbox.response.CheckboxResponse;
import com.vibent.vibentback.bubble.checkbox.response.CheckboxResponseRepository;
import com.vibent.vibentback.bubble.checkbox.usersResponses.UsersCheckboxResponses;
import com.vibent.vibentback.bubble.checkbox.usersResponses.UsersCheckboxResponsesRepository;
import com.vibent.vibentback.user.User;
import com.vibent.vibentback.user.UserRepository;
import com.vibent.vibentback.bubble.checkbox.CheckboxBubble;
import com.vibent.vibentback.bubble.checkbox.CheckboxBubbleRepository;
import com.vibent.vibentback.bubble.checkbox.response.CheckboxResponse;
import com.vibent.vibentback.bubble.checkbox.response.CheckboxResponseRepository;
import com.vibent.vibentback.bubble.checkbox.usersResponses.UsersCheckboxResponses;
import com.vibent.vibentback.bubble.checkbox.usersResponses.UsersCheckboxResponsesRepository;
import com.vibent.vibentback.user.User;
import com.vibent.vibentback.user.UserRepository;
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
    public void testAddUsersCheckboxResponses(){
        UsersCheckboxResponses usersCheckboxResponses = new UsersCheckboxResponses(user.getRef(), checkboxResponse.getId());
        repository.save(usersCheckboxResponses);
    }
}
