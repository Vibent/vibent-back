package com.gitlab.vibent.vibentback.bubble;

import com.gitlab.vibent.vibentback.VibentTests;
import com.gitlab.vibent.vibentback.bubble.alimentation.AlimentationBubble;
import com.gitlab.vibent.vibentback.bubble.alimentation.AlimentationBubbleRepository;
import com.gitlab.vibent.vibentback.bubble.alimentation.AlimentationEntry;
import com.gitlab.vibent.vibentback.bubble.alimentation.AlimentationEntryRepository;
import com.gitlab.vibent.vibentback.bubble.checkbox.*;
import com.gitlab.vibent.vibentback.bubble.ownership.BubbleOwnership;
import com.gitlab.vibent.vibentback.bubble.ownership.BubbleOwnershipRepository;
import com.gitlab.vibent.vibentback.bubble.survey.SurveyBubble;
import com.gitlab.vibent.vibentback.bubble.survey.SurveyBubbleRepository;
import com.gitlab.vibent.vibentback.bubble.survey.SurveyResponse;
import com.gitlab.vibent.vibentback.bubble.survey.SurveyResponseRepository;
import com.gitlab.vibent.vibentback.event.Event;
import com.gitlab.vibent.vibentback.event.EventRepository;
import com.gitlab.vibent.vibentback.groupT.GroupT;
import com.gitlab.vibent.vibentback.groupT.GroupTRepository;
import com.gitlab.vibent.vibentback.user.User;
import com.gitlab.vibent.vibentback.user.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.UUID;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SurveyResponseTests extends VibentTests {

    @Autowired
    SurveyResponseRepository repository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SurveyBubbleRepository surveyRepository;


    User user;
    SurveyBubble surveyBubble;

    @Before
    public void init()
    {
        /** User creation **/
        user = new User(UUID.randomUUID().toString(),"conor","ryan","cr.sd.sd@gmail.com","secret","sel");
        userRepository.save(user);
        /** Bubble creation **/
        surveyBubble = new SurveyBubble("survetTest");
        surveyRepository.save(surveyBubble);
    }

    @Test
    public void testAddSurveyResponse(){
        SurveyResponse surveyResponse = new SurveyResponse( surveyBubble.getId(),user.getRef(),"responseContent");
        repository.save(surveyResponse);
    }
}
