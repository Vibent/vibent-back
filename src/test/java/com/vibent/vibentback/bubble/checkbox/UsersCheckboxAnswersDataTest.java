package com.vibent.vibentback.bubble.checkbox;

import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.bubble.checkbox.answer.CheckboxAnswer;
import com.vibent.vibentback.bubble.checkbox.answer.CheckboxAnswerRepository;
import com.vibent.vibentback.bubble.checkbox.usersAnswers.UsersCheckboxAnswers;
import com.vibent.vibentback.bubble.checkbox.usersAnswers.UsersCheckboxAnswersRepository;
import com.vibent.vibentback.user.User;
import com.vibent.vibentback.user.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UsersCheckboxAnswersDataTest extends VibentTest {

    @Autowired
    UsersCheckboxAnswersRepository repository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CheckboxAnswerRepository checkboxAnswerRepository;

    @Autowired
    CheckboxBubbleRepository checkboxBubbleRepository;

    User user;
    CheckboxAnswer checkboxAnswer;
    CheckboxBubble checkboxBubble;

    @Before
    public void init()
    {
        /** User creation **/
        user = new User(UUID.randomUUID().toString(),"conor","ryan","cr.sd.sd@gmail.com","secret","sel");
        userRepository.save(user);
        /** Bubble creation **/
        checkboxBubble = new CheckboxBubble("checkboxBubbleTitle");
        checkboxBubbleRepository.save(checkboxBubble);
        /** Bubble answer creation **/
        checkboxAnswer = new CheckboxAnswer(checkboxBubble.getId(), "answer");
        checkboxAnswerRepository.save(checkboxAnswer);
    }

    @Test
    public void testAddUsersCheckboxAnswers(){
        UsersCheckboxAnswers usersCheckboxAnswers = new UsersCheckboxAnswers(user.getRef(), checkboxAnswer.getId());
        repository.save(usersCheckboxAnswers);
    }
}
