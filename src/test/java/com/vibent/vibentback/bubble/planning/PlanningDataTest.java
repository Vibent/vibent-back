package com.vibent.vibentback.bubble.planning;

import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.bubble.planning.entry.PlanningEntry;
import com.vibent.vibentback.bubble.planning.entry.PlanningEntryRepository;
import com.vibent.vibentback.event.EventRepository;
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
@RunWith(SpringRunner.class)
@SpringBootTest
public class PlanningDataTest extends VibentTest {

    @Autowired
    PlanningBubbleRepository bubbleRepository;
    @Autowired
    PlanningEntryRepository entryRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    EventRepository eventRepository;

    PlanningEntry RANDOM_ENTRY;
    PlanningBubble RANDOM_BUBBLE;

    @Before
    public void setUp() {
        super.setUp();

        RANDOM_USER = userRepository.save(RANDOM_USER);
        RANDOM_EVENT = eventRepository.save(RANDOM_EVENT);

        log.info("{}", RANDOM_EVENT.getRef());

        RANDOM_BUBBLE = new PlanningBubble();
        RANDOM_BUBBLE.setDeleted(false);
        RANDOM_BUBBLE.setCreator(RANDOM_USER);
        RANDOM_BUBBLE.setEvent(RANDOM_EVENT);
        RANDOM_BUBBLE.setTitle("Title For Test");
        RANDOM_BUBBLE = bubbleRepository.save(RANDOM_BUBBLE);

        RANDOM_ENTRY = new PlanningEntry();
        RANDOM_ENTRY.setDeleted(false);
        RANDOM_ENTRY.setUser(RANDOM_USER);
        RANDOM_ENTRY.setBubble(RANDOM_BUBBLE);

        RANDOM_ENTRY.setContent("Entry For Test");
        RANDOM_ENTRY.setStart(getFutureDate(4));
        RANDOM_ENTRY.setEnd(getFutureDate(5));

    }

    @Test
    public void testAddPlanningBubble() {
        RANDOM_BUBBLE = bubbleRepository.save(RANDOM_BUBBLE);

        Assert.assertNotNull(bubbleRepository.findById(RANDOM_BUBBLE.getId()));
    }

    @Test
    public void testAddPlanningEntry() {
        RANDOM_ENTRY = entryRepository.save(RANDOM_ENTRY);

        Assert.assertNotNull(entryRepository.findById(RANDOM_ENTRY.getId()));
    }

}
