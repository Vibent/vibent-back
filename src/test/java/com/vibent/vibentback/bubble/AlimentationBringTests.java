package com.vibent.vibentback.bubble;

import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.bubble.alimentation.AlimentationBubble;
import com.vibent.vibentback.bubble.alimentation.AlimentationBubbleRepository;
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

import java.util.UUID;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AlimentationBringTests extends VibentTest {

    @Autowired
    AlimentationBringRepository repository;

    @Autowired
    AlimentationBubbleRepository bubbleRepository;

    @Autowired
    AlimentationEntryRepository alimentationEntryRepository;

    @Autowired
    UserRepository userRepository;

    User user;
    AlimentationBubble alimentationBubble;
    AlimentationEntry alimentationEntry;

    @Before
    public void init() {
        /** User creation **/
        user = new User(UUID.randomUUID().toString(), "conor", "ryan", "cr.sd.sd@gmail.com", "secret", "sel");
        userRepository.save(user);
        /** Bubble creation **/
        alimentationBubble = new AlimentationBubble();
        bubbleRepository.save(alimentationBubble);
        /**Entry creation**/
        alimentationEntry = new AlimentationEntry(alimentationBubble.getId(), "Coca", "Drink");
        alimentationEntryRepository.save(alimentationEntry);
    }

    @Test
    public void testAddAlimentationBringing() {
        AlimentationBring AlimentationBring = new AlimentationBring(alimentationEntry.getId(), user.getRef());
        repository.save(AlimentationBring);
    }
}
