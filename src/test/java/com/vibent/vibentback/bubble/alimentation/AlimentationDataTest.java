package com.vibent.vibentback.bubble.alimentation;

import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.bubble.alimentation.bring.AlimentationBring;
import com.vibent.vibentback.bubble.alimentation.bring.AlimentationBringRepository;
import com.vibent.vibentback.bubble.alimentation.entry.AlimentationEntry;
import com.vibent.vibentback.bubble.alimentation.entry.AlimentationEntryRepository;
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
import java.util.HashSet;

@Slf4j
@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class AlimentationDataTest extends VibentTest {

    @Autowired
    AlimentationBringRepository bringRepository;
    @Autowired
    AlimentationBubbleRepository bubbleRepository;
    @Autowired
    AlimentationEntryRepository entryRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    EventRepository eventRepository;

    private AlimentationEntry RANDOM_ENTRY;
    private AlimentationBubble RANDOM_BUBBLE;
    private AlimentationBring RANDOM_BRING;

    @Before
    public void setUp() {
        super.setUp();

        RANDOM_USER = userRepository.save(RANDOM_USER);
        RANDOM_EVENT = eventRepository.save(RANDOM_EVENT);

        RANDOM_BUBBLE = new AlimentationBubble();
        RANDOM_BUBBLE.setDeleted(false);
        RANDOM_BUBBLE.setCreator(RANDOM_USER);
        RANDOM_BUBBLE.setEvent(RANDOM_EVENT);
        RANDOM_BUBBLE.setEntries(new HashSet<AlimentationEntry>() {{
            add(RANDOM_ENTRY);
        }});

        RANDOM_ENTRY = new AlimentationEntry();
        RANDOM_ENTRY.setDeleted(false);
        RANDOM_ENTRY.setBubble(RANDOM_BUBBLE);
        RANDOM_ENTRY.setName("coke");
        RANDOM_ENTRY.setTotalRequested(100);
        RANDOM_ENTRY.setType(AlimentationEntry.Type.FOOD);
        RANDOM_ENTRY.setBrings(new HashSet<AlimentationBring>() {{
            add(RANDOM_BRING);
        }});

        RANDOM_BRING = new AlimentationBring();
        RANDOM_BRING.setEntry(RANDOM_ENTRY);
        RANDOM_BRING.setUser(RANDOM_USER);
        RANDOM_BRING.setDeleted(false);
        RANDOM_BRING.setQuantity(5);
    }

    @Test
    public void testAddAlimentationBubble() {
        RANDOM_BUBBLE = bubbleRepository.save(RANDOM_BUBBLE);

        Assert.assertNotNull(bubbleRepository.findById(RANDOM_BUBBLE.getId()));
    }

    @Test
    public void testAddAlimentationEntry() {
        RANDOM_ENTRY = entryRepository.save(RANDOM_ENTRY);

        Assert.assertNotNull(entryRepository.findById(RANDOM_ENTRY.getId()));
    }

    @Test
    public void testAddAlimentationBring() {
        RANDOM_BRING.setEntry(entryRepository.save(RANDOM_ENTRY));
        RANDOM_BRING = bringRepository.save(RANDOM_BRING);

        Assert.assertNotNull(bringRepository.findById(RANDOM_BRING.getId()));
    }
}
