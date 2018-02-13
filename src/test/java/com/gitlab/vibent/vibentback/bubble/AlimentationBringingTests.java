package com.gitlab.vibent.vibentback.bubble;

import com.gitlab.vibent.vibentback.VibentTests;
import com.gitlab.vibent.vibentback.bubble.alimentation.*;
import com.gitlab.vibent.vibentback.bubble.alimentation.bringing.AlimentationBringing;
import com.gitlab.vibent.vibentback.bubble.alimentation.bringing.AlimentationBringingRepository;
import com.gitlab.vibent.vibentback.bubble.alimentation.entry.AlimentationEntry;
import com.gitlab.vibent.vibentback.bubble.alimentation.entry.AlimentationEntryRepository;
import com.gitlab.vibent.vibentback.user.User;
import com.gitlab.vibent.vibentback.user.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AlimentationBringingTests extends VibentTests {

    @Autowired
    AlimentationBringingRepository repository;

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
    public void init()
    {
        /** User creation **/
        user = new User(UUID.randomUUID().toString(),"conor","ryan","cr.sd.sd@gmail.com","secret","sel");
        userRepository.save(user);
        /** Bubble creation **/
        alimentationBubble = new AlimentationBubble();
        bubbleRepository.save(alimentationBubble);
        /**Entry creation**/
        alimentationEntry = new AlimentationEntry(alimentationBubble.getId(), "Coca", "Drink");
        alimentationEntryRepository.save(alimentationEntry);
    }

    @Test
    public void testAddAlimentationBringing(){
        AlimentationBringing AlimentationBringing = new AlimentationBringing(alimentationEntry.getId(), user.getRef());
        repository.save(AlimentationBringing);
    }
}
