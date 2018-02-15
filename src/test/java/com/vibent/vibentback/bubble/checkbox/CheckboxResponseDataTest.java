package com.vibent.vibentback.bubble.checkbox;


import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.bubble.checkbox.CheckboxBubble;
import com.vibent.vibentback.bubble.checkbox.CheckboxBubbleRepository;
import com.vibent.vibentback.bubble.checkbox.response.CheckboxResponse;
import com.vibent.vibentback.bubble.checkbox.response.CheckboxResponseRepository;
import com.vibent.vibentback.bubble.checkbox.CheckboxBubble;
import com.vibent.vibentback.bubble.checkbox.CheckboxBubbleRepository;
import com.vibent.vibentback.bubble.checkbox.response.CheckboxResponse;
import com.vibent.vibentback.bubble.checkbox.response.CheckboxResponseRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CheckboxResponseDataTest extends VibentTest {

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
