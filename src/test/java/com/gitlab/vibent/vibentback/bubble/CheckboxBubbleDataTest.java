package com.gitlab.vibent.vibentback.bubble;

import com.gitlab.vibent.vibentback.VibentTest;
import com.gitlab.vibent.vibentback.bubble.checkbox.CheckboxBubble;
import com.gitlab.vibent.vibentback.bubble.checkbox.CheckboxBubbleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CheckboxBubbleDataTest extends VibentTest {

    @Autowired
    CheckboxBubbleRepository repository;

    @Test
    public void testAddCheckboxBubble(){
        CheckboxBubble checkboxBubble = new CheckboxBubble("checkboxTitle");
        repository.save(checkboxBubble);
    }
}