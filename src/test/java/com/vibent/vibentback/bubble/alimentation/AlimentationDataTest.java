package com.vibent.vibentback.bubble.alimentation;

import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.bubble.alimentation.bring.AlimentationBring;
import com.vibent.vibentback.bubble.alimentation.bring.AlimentationBringRepository;
import com.vibent.vibentback.bubble.alimentation.entry.AlimentationEntry;
import com.vibent.vibentback.bubble.alimentation.entry.AlimentationEntryRepository;
import com.vibent.vibentback.user.User;
import com.vibent.vibentback.user.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AlimentationDataTest extends VibentTest {

    @Autowired
    AlimentationBringRepository bringRepository;
    @Autowired
    AlimentationBubbleRepository bubbleRepository;
    @Autowired
    AlimentationEntryRepository entryRepository;
    @Autowired
    UserRepository userRepository;

    private User user;
    private AlimentationBubble alimentationBubble;
    private AlimentationEntry alimentationEntry;

    @Before
    public void setUp()
    {
        super.setUp();
        /** User creation **/
        user = userRepository.save(RANDOM_USER);
        /** Bubble creation **/
        alimentationBubble = bubbleRepository.save(new AlimentationBubble());
        /**Entry creation**/
        alimentationEntry = entryRepository.save(new AlimentationEntry(alimentationBubble.getId(), "Coca", AlimentationEntry.Type.Drink));
    }

    @Test
    public void testAddAlimentationBring() {
        AlimentationBring AlimentationBring = new AlimentationBring(alimentationEntry.getId(), user.getRef());
        bringRepository.save(AlimentationBring);
    }

    @Test
    public void testAddAlimentationBubble(){
        AlimentationBubble alimentationBubble = new AlimentationBubble();
        bubbleRepository.save(alimentationBubble);
    }

    @Test
    public void testAddAlimentationEntry(){
        AlimentationEntry alimentationEntry = new AlimentationEntry(alimentationBubble.getId(), "Coca", AlimentationEntry.Type.Drink);
        entryRepository.save(alimentationEntry);
    }
}
