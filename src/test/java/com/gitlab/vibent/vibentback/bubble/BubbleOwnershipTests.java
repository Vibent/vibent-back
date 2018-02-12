package com.gitlab.vibent.vibentback.bubble;

import com.gitlab.vibent.vibentback.VibentTests;
import com.gitlab.vibent.vibentback.bubble.alimentation.AlimentationBubble;
import com.gitlab.vibent.vibentback.bubble.alimentation.AlimentationBubbleRepository;
import com.gitlab.vibent.vibentback.bubble.alimentation.AlimentationEntry;
import com.gitlab.vibent.vibentback.bubble.alimentation.AlimentationEntryRepository;
import com.gitlab.vibent.vibentback.bubble.checkbox.CheckboxBubble;
import com.gitlab.vibent.vibentback.bubble.checkbox.CheckboxBubbleRepository;
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
public class BubbleOwnershipTests extends VibentTests {

    @Autowired
    BubbleOwnershipRepository repository;

    @Autowired
    AlimentationBubbleRepository bubbleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    GroupTRepository groupRepository;

    AlimentationBubble alimentationBubble;
    User user;
    Event event;
    GroupT group;

    @Before
    public void init()
    {
        /** Bubble creation **/
        alimentationBubble = new AlimentationBubble();
        bubbleRepository.save(alimentationBubble);
        /** Group creation **/
        group = new GroupT(UUID.randomUUID().toString(), "groupTest");
        groupRepository.save(group);
        /** User creation **/
        user = new User(UUID.randomUUID().toString(),"conor","ryan","cr.sd.sd@gmail.com","secret","sel");
        userRepository.save(user);
        /** Event creation **/
        event = new Event(UUID.randomUUID().toString(), group.getRef(), "eventTest", "description",new Date(), new Date());
        eventRepository.save(event);
    }

    @Test
    public void testAddBubbleOwnership(){
        BubbleOwnership bubbleOwnership = new BubbleOwnership(event.getRef(), alimentationBubble.getId(), "AlimentationBubble", user.getRef());
        repository.save(bubbleOwnership);
    }
}
