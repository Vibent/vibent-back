package com.gitlab.vibent.vibentback.bubble;

import com.gitlab.vibent.vibentback.VibentTests;
import com.gitlab.vibent.vibentback.bubble.checkbox.CheckboxBubble;
import com.gitlab.vibent.vibentback.bubble.checkbox.CheckboxBubbleRepository;
import com.gitlab.vibent.vibentback.bubble.free.FreeBubble;
import com.gitlab.vibent.vibentback.bubble.free.FreeBubbleRepository;
import com.gitlab.vibent.vibentback.bubble.survey.SurveyBubble;
import com.gitlab.vibent.vibentback.bubble.survey.SurveyBubbleRepository;
import com.gitlab.vibent.vibentback.bubble.travel.TravelBubble;
import com.gitlab.vibent.vibentback.bubble.travel.TravelBubbleRepository;
import com.gitlab.vibent.vibentback.bubble.travel.TravelProposal;
import com.gitlab.vibent.vibentback.bubble.travel.TravelProposalRepository;
import com.gitlab.vibent.vibentback.user.User;
import com.gitlab.vibent.vibentback.user.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TravelProposalTests extends VibentTests {

    @Autowired
    TravelProposalRepository repository;

    @Autowired
    TravelBubbleRepository travelRepository;

    @Autowired
    UserRepository userRepository;

    User user;
    TravelBubble travelBubble;

    @Before
    public void init()
    {
        user = new User(UUID.randomUUID().toString(),"conor","ryan","cr.sd.sd@gmail.com","secret","sel");
        userRepository.save(user);
        travelBubble = new TravelBubble();
        travelRepository.save(travelBubble);
    }
    @Test
    public void testAddTravelProposal(){
        TravelProposal travelProposal = new TravelProposal(travelBubble.getId(), user.getRef(), "{4685.92,85.29941;1989,528891");
        repository.save(travelProposal);
    }

}
