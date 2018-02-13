package com.gitlab.vibent.vibentback.bubble;

import com.gitlab.vibent.vibentback.VibentTests;
import com.gitlab.vibent.vibentback.bubble.alimentation.AlimentationBubble;
import com.gitlab.vibent.vibentback.bubble.alimentation.AlimentationBubbleRepository;
import com.gitlab.vibent.vibentback.bubble.alimentation.AlimentationEntry;
import com.gitlab.vibent.vibentback.bubble.alimentation.AlimentationEntryRepository;
import com.gitlab.vibent.vibentback.bubble.checkbox.*;
import com.gitlab.vibent.vibentback.bubble.ownership.BubbleOwnership;
import com.gitlab.vibent.vibentback.bubble.ownership.BubbleOwnershipRepository;
import com.gitlab.vibent.vibentback.event.Event;
import com.gitlab.vibent.vibentback.event.EventRepository;
import com.gitlab.vibent.vibentback.groupT.GroupT;
import com.gitlab.vibent.vibentback.groupT.GroupTRepository;
import com.gitlab.vibent.vibentback.user.User;
import com.gitlab.vibent.vibentback.user.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.UUID;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UsersCheckboxResponsesTests extends VibentTests {

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
