package com.vibent.vibentback.bubble.checkbox;


import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.bubble.checkbox.answer.CheckboxAnswer;
import com.vibent.vibentback.bubble.checkbox.answer.CheckboxAnswerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CheckboxAnswerDataTest extends VibentTest {

    @Autowired
    CheckboxAnswerRepository repository;

    @Autowired
    CheckboxBubbleRepository bubbleRepository;

    CheckboxBubble checkboxBubble;


    @Before
    public void setUp() {
        super.setUp();
        checkboxBubble = new CheckboxBubble("checkboxBubbleTitle");
        bubbleRepository.save(checkboxBubble);
    }

    @Test
    public void testAddCheckboxAnswer() {
        CheckboxAnswer checkboxAnswer = new CheckboxAnswer(checkboxBubble.getId(), "content");
        repository.save(checkboxAnswer);
    }
}
