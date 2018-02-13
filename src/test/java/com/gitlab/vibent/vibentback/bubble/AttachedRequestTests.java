package com.gitlab.vibent.vibentback.bubble;

import com.gitlab.vibent.vibentback.VibentTests;
import com.gitlab.vibent.vibentback.bubble.checkbox.*;
import com.gitlab.vibent.vibentback.bubble.survey.*;
import com.gitlab.vibent.vibentback.bubble.travel.*;
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
public class AttachedRequestTests extends VibentTests {

    @Autowired
    AttachedRequestRepository repository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TravelProposalRepository proposalRepository;

    @Autowired
    TravelRequestRepository requestRepository;

    @Autowired
    TravelBubbleRepository travelBubbleRepository;

    User user;
    TravelBubble travelBubble;
    TravelProposal travelProposal;
    TravelRequest travelRequest;

    @Before
    public void init()
    {
        /** User creation **/
        user = new User(UUID.randomUUID().toString(),"conor","ryan","cr.sd.sd@gmail.com","secret","sel");
        userRepository.save(user);
        /** Bubble creation **/
        travelBubble = new TravelBubble();
        travelBubbleRepository.save(travelBubble);
        /** TravelProposal creation **/
        travelProposal = new TravelProposal(travelBubble.getId(), user.getRef(), "{4685.92,85.29941;1989,528891");
        proposalRepository.save(travelProposal);
        /** TravelRequest creation **/
        travelRequest = new TravelRequest(travelBubble.getId(), user.getRef());
        requestRepository.save(travelRequest);
    }

    @Test
    public void testAddAttachedRequest(){
        AttachedRequest attachedRequest = new AttachedRequest(travelProposal.getId(), travelRequest.getId());
        repository.save(attachedRequest);
    }
}
