package com.gitlab.vibent.vibentback.bubble;

import com.gitlab.vibent.vibentback.VibentInternalTest;
import com.gitlab.vibent.vibentback.bubble.checkbox.CheckboxBubble;
import com.gitlab.vibent.vibentback.bubble.checkbox.CheckboxBubbleRepository;
import com.gitlab.vibent.vibentback.bubble.checkbox.CheckboxResponse;
import com.gitlab.vibent.vibentback.bubble.checkbox.CheckboxResponseRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CheckboxResponseDataTest extends VibentInternalTest {

    @Autowired
    CheckboxResponseRepository repository;

    @Autowired
    CheckboxBubbleRepository bubbleRepository;

    CheckboxBubble checkboxBubble;


    @Before
    public void init()
    {
       checkboxBubble = new CheckboxBubble("checkboxBubbleTitle");
       bubbleRepository.save(checkboxBubble);
    }

    @Test
    public void testAddCheckboxResponse(){
        CheckboxResponse checkboxResponse = new CheckboxResponse(checkboxBubble.getId(), "content");
        repository.save(checkboxResponse);
    }
}
