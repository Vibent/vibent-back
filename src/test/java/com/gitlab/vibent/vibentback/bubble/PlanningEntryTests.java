package com.gitlab.vibent.vibentback.bubble;

import com.gitlab.vibent.vibentback.VibentTests;
import com.gitlab.vibent.vibentback.bubble.alimentation.AlimentationBubble;
import com.gitlab.vibent.vibentback.bubble.alimentation.AlimentationBubbleRepository;
import com.gitlab.vibent.vibentback.bubble.alimentation.AlimentationEntry;
import com.gitlab.vibent.vibentback.bubble.alimentation.AlimentationEntryRepository;
import com.gitlab.vibent.vibentback.bubble.checkbox.CheckboxBubble;
import com.gitlab.vibent.vibentback.bubble.checkbox.CheckboxBubbleRepository;
import com.gitlab.vibent.vibentback.bubble.checkbox.CheckboxResponse;
import com.gitlab.vibent.vibentback.bubble.checkbox.CheckboxResponseRepository;
import com.gitlab.vibent.vibentback.bubble.ownership.BubbleOwnership;
import com.gitlab.vibent.vibentback.bubble.ownership.BubbleOwnershipRepository;
import com.gitlab.vibent.vibentback.bubble.planning.PlanningBubble;
import com.gitlab.vibent.vibentback.bubble.planning.PlanningBubbleRepository;
import com.gitlab.vibent.vibentback.bubble.planning.PlanningEntry;
import com.gitlab.vibent.vibentback.bubble.planning.PlanningEntryRepository;
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
public class PlanningEntryTests extends VibentTests {

    @Autowired
    PlanningEntryRepository repository;

    @Autowired
    PlanningBubbleRepository bubbleRepository;

    @Autowired
    UserRepository userRepository;

    PlanningBubble planningBubble;
    User user;


    @Before
    public void init()
    {
        planningBubble = new PlanningBubble();
        bubbleRepository.save(planningBubble);
        user = new User(UUID.randomUUID().toString(),"conor","ryan","cr.sd.sd@gmail.com","secret","sel");
        userRepository.save(user);
    }

    @Test
    public void testAddPlanningEntry(){
        PlanningEntry planningEntry = new PlanningEntry(planningBubble.getId(),user.getRef(), new Date(), new Date(), "planningContent");
        repository.save(planningEntry);
    }
}
