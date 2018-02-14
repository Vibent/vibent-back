package com.vibent.vibentback.bubble;

import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.bubble.alimentation.AlimentationBubble;
import com.vibent.vibentback.bubble.alimentation.AlimentationBubbleRepository;
import com.vibent.vibentback.bubble.alimentation.AlimentationBubble;
import com.vibent.vibentback.bubble.alimentation.AlimentationBubbleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AlimentationBubbleDataTest extends VibentTest {

    @Autowired
    AlimentationBubbleRepository repository;

    @Test
    public void testAddAlimentationBubble(){
        AlimentationBubble alimentationBubble = new AlimentationBubble();
        repository.save(alimentationBubble);
    }
}