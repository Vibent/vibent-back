package com.gitlab.vibent.vibentback.bubble;

import com.gitlab.vibent.vibentback.VibentTests;
import com.gitlab.vibent.vibentback.bubble.alimentation.AlimentationBubble;
import com.gitlab.vibent.vibentback.bubble.alimentation.AlimentationBubbleRepository;
import com.gitlab.vibent.vibentback.bubble.alimentation.entry.AlimentationEntry;
import com.gitlab.vibent.vibentback.bubble.alimentation.entry.AlimentationEntryRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AlimentationEntryTests extends VibentTests {

    @Autowired
    AlimentationEntryRepository repository;

    @Autowired
    AlimentationBubbleRepository bubbleRepository;

    AlimentationBubble alimentationBubble;

    @Before
    public void init()
    {
        alimentationBubble = new AlimentationBubble();
        bubbleRepository.save(alimentationBubble);
    }

    @Test
    public void testAddAlimentationEntry(){
        AlimentationEntry alimentationEntry = new AlimentationEntry(alimentationBubble.getId(), "Coca", "Drink");
        repository.save(alimentationEntry);
    }
}
