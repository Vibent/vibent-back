package com.vibent.vibentback.bubble.checkbox;

import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.bubble.BubbleType;
import com.vibent.vibentback.bubble.checkbox.answer.CheckboxAnswer;
import com.vibent.vibentback.bubble.checkbox.answer.CheckboxAnswerRepository;
import com.vibent.vibentback.bubble.checkbox.option.CheckboxOption;
import com.vibent.vibentback.bubble.checkbox.option.CheckboxOptionRepository;
import com.vibent.vibentback.event.EventRepository;
import com.vibent.vibentback.groupT.GroupTRepository;
import com.vibent.vibentback.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

@Slf4j
@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class CheckboxDataTest extends VibentTest {

    @Autowired
    CheckboxAnswerRepository checkboxAnswerRepository;
    @Autowired
    CheckboxRepository bubbleRepository;
    @Autowired
    CheckboxOptionRepository responseRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    EventRepository eventRepository;
    @Autowired
    GroupTRepository groupTRepository;

    CheckboxOption RANDOM_OPTION;
    CheckboxBubble RANDOM_BUBBLE;
    CheckboxAnswer RANDOM_ANSWER;

    @Before
    public void setUp() {
        super.setUp();

        RANDOM_USER = userRepository.save(RANDOM_USER);
        RANDOM_GROUP = groupTRepository.save(RANDOM_GROUP);
        RANDOM_EVENT = eventRepository.save(RANDOM_EVENT);

        RANDOM_BUBBLE = new CheckboxBubble();
        RANDOM_BUBBLE.setDeleted(false);
        RANDOM_BUBBLE.setCreator(RANDOM_USER);
        RANDOM_BUBBLE.setEvent(RANDOM_EVENT);
        RANDOM_BUBBLE.setType(BubbleType.CheckboxBubble);
        RANDOM_BUBBLE.setTitle("Title For Test");
        RANDOM_BUBBLE = bubbleRepository.save(RANDOM_BUBBLE);

        RANDOM_OPTION = new CheckboxOption();
        RANDOM_OPTION.setDeleted(false);
        RANDOM_OPTION.setUser(RANDOM_USER);
        RANDOM_OPTION.setBubble(RANDOM_BUBBLE);
        RANDOM_OPTION.setContent("Response For Test");

        RANDOM_ANSWER = new CheckboxAnswer();
        RANDOM_ANSWER.setOption(RANDOM_OPTION);
        RANDOM_ANSWER.setUser(RANDOM_USER);
        RANDOM_ANSWER.setDeleted(false);

    }

    @Test
    public void testAddCheckboxBubble() {
        RANDOM_BUBBLE = bubbleRepository.save(RANDOM_BUBBLE);

        Assert.assertNotNull(bubbleRepository.findById(RANDOM_BUBBLE.getId()));
    }

    @Test
    public void testAddCheckboxResponse() {
        RANDOM_OPTION = responseRepository.save(RANDOM_OPTION);

        Assert.assertNotNull(responseRepository.findById(RANDOM_OPTION.getId()));
    }

    @Test
    public void testAddUserResponse() {
        RANDOM_ANSWER.setOption(responseRepository.save(RANDOM_OPTION));
        RANDOM_ANSWER = checkboxAnswerRepository.save(RANDOM_ANSWER);

        Assert.assertNotNull(checkboxAnswerRepository.findById(RANDOM_ANSWER.getId()));
    }
}
