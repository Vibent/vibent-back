package com.vibent.vibentback.bubble;

import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.bubble.free.FreeBubble;
import com.vibent.vibentback.bubble.free.FreeBubbleRepository;
import com.vibent.vibentback.bubble.free.FreeBubble;
import com.vibent.vibentback.bubble.free.FreeBubbleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class FreeBubbleDataTest extends VibentTest {

    @Autowired
    FreeBubbleRepository repository;

    @Test
    public void testAddFreeBubble(){
        FreeBubble freeBubble = new FreeBubble("freeBubbleTitle", "freeBubbleContent");
        repository.save(freeBubble);
    }

}
