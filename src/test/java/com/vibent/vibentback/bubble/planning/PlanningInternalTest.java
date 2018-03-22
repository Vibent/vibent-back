package com.vibent.vibentback.bubble.planning;

import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.bubble.planning.PlanningBubble;
import com.vibent.vibentback.bubble.planning.PlanningBubbleRepository;
import com.vibent.vibentback.bubble.planning.entry.PlanningEntry;
import com.vibent.vibentback.bubble.planning.entry.PlanningEntryRepository;
import com.vibent.vibentback.user.User;
import com.vibent.vibentback.user.UserRepository;
import com.vibent.vibentback.bubble.planning.entry.PlanningEntry;
import com.vibent.vibentback.bubble.planning.entry.PlanningEntryRepository;
import com.vibent.vibentback.user.User;
import com.vibent.vibentback.user.UserRepository;
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
public class PlanningEntryDataTest extends VibentTest {

    @Autowired
    PlanningEntryRepository repository;

    @Autowired
    PlanningBubbleRepository bubbleRepository;

    @Autowired
    UserRepository userRepository;

    PlanningBubble planningBubble;

    User user;

    @Before
    public void setUp()
    {
        super.setUp();
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
