package com.vibent.vibentback.bubble.checkbox;

import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.bubble.BubbleType;
import com.vibent.vibentback.bubble.checkbox.entry.CheckboxResponse;
import com.vibent.vibentback.bubble.checkbox.entry.CheckboxResponseRepository;
import com.vibent.vibentback.bubble.checkbox.usersResponses.UsersCheckboxResponses;
import com.vibent.vibentback.bubble.checkbox.usersResponses.UsersCheckboxResponsesRepository;
import com.vibent.vibentback.event.EventRepository;
import com.vibent.vibentback.groupT.GroupTRepository;
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
public class CheckboxDataTest extends VibentTest {

    @Autowired
    UsersCheckboxResponsesRepository usersCheckboxResponsesRepository;
    @Autowired
    CheckboxRepository bubbleRepository;
    @Autowired
    CheckboxResponseRepository responseRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    EventRepository eventRepository;
    @Autowired
    GroupTRepository groupTRepository;

    CheckboxResponse RANDOM_RESPONSE;
    CheckboxBubble RANDOM_BUBBLE;
    UsersCheckboxResponses RANDOM_USER_RESPONSE;

    @Before
    public void setUp()
    {
        super.setUp();

        RANDOM_USER = userRepository.save(RANDOM_USER);
        RANDOM_GROUP = groupTRepository.save(RANDOM_GROUP);
        RANDOM_EVENT = eventRepository.save(RANDOM_EVENT);

        log.info("{}",RANDOM_EVENT.getRef());

        RANDOM_BUBBLE = new CheckboxBubble();
        RANDOM_BUBBLE.setDeleted(false);
        RANDOM_BUBBLE.setCreator(RANDOM_USER);
        RANDOM_BUBBLE.setEvent(RANDOM_EVENT);
        RANDOM_BUBBLE.setType(BubbleType.CheckboxBubble);
        RANDOM_BUBBLE.setTitle("Title For Test");
        RANDOM_BUBBLE = bubbleRepository.save(RANDOM_BUBBLE);

        RANDOM_RESPONSE = new CheckboxResponse();
        RANDOM_RESPONSE.setDeleted(false);
        RANDOM_RESPONSE.setUser(RANDOM_USER);
        RANDOM_RESPONSE.setBubble(RANDOM_BUBBLE);
        RANDOM_RESPONSE.setContent("Response For Test");

        RANDOM_USER_RESPONSE = new UsersCheckboxResponses();
        RANDOM_USER_RESPONSE.setResponse(RANDOM_RESPONSE);
        RANDOM_USER_RESPONSE.setUser(RANDOM_USER);
        RANDOM_USER_RESPONSE.setDeleted(false);

    }

    @Test
    public void testAddCheckboxBubble(){
        RANDOM_BUBBLE = bubbleRepository.save(RANDOM_BUBBLE);

        Assert.assertNotNull(bubbleRepository.findById(RANDOM_BUBBLE.getId()));
    }

    @Test
    public void testAddCheckboxResponse(){
        RANDOM_RESPONSE = responseRepository.save(RANDOM_RESPONSE);

        Assert.assertNotNull(responseRepository.findById(RANDOM_RESPONSE.getId()));
    }

    @Test
    public void testAddUserResponse() {
        RANDOM_USER_RESPONSE.setResponse(responseRepository.save(RANDOM_RESPONSE));
        RANDOM_USER_RESPONSE = usersCheckboxResponsesRepository.save(RANDOM_USER_RESPONSE);

        Assert.assertNotNull(usersCheckboxResponsesRepository.findById(RANDOM_USER_RESPONSE.getId()));
    }
}
