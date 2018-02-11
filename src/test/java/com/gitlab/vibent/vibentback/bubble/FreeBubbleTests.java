package com.gitlab.vibent.vibentback.bubble;

import com.gitlab.vibent.vibentback.VibentTests;
import com.gitlab.vibent.vibentback.bubble.checkbox.CheckboxBubble;
import com.gitlab.vibent.vibentback.bubble.checkbox.CheckboxBubbleRepository;
import com.gitlab.vibent.vibentback.bubble.free.FreeBubble;
import com.gitlab.vibent.vibentback.bubble.free.FreeBubbleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class FreeBubbleTests extends VibentTests {

    @Autowired
    FreeBubbleRepository repository;

    @Test
    public void testAddFreeBubble(){
        FreeBubble freeBubble = new FreeBubble("freeBubbleTitle", "freeBubbleContent");
        repository.save(freeBubble);
    }

}
