package com.vibent.vibentback.bubble;

import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.bubble.alimentation.AlimentationBubble;
import com.vibent.vibentback.bubble.alimentation.AlimentationBubbleRepository;
import com.vibent.vibentback.bubble.alimentation.entry.AlimentationEntry;
import com.vibent.vibentback.bubble.alimentation.entry.AlimentationEntryRepository;
import com.vibent.vibentback.bubble.alimentation.AlimentationBubble;
import com.vibent.vibentback.bubble.alimentation.AlimentationBubbleRepository;
import com.vibent.vibentback.bubble.alimentation.entry.AlimentationEntry;
import com.vibent.vibentback.bubble.alimentation.entry.AlimentationEntryRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AlimentationEntryDataTest extends VibentTest {

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
