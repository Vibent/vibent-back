package com.vibent.vibentback.bubble;


import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.bubble.travel.TravelBubble;
import com.vibent.vibentback.bubble.travel.TravelBubbleRepository;
import com.vibent.vibentback.bubble.travel.attachedRequest.AttachedRequest;
import com.vibent.vibentback.bubble.travel.attachedRequest.AttachedRequestRepository;
import com.vibent.vibentback.bubble.travel.proposal.TravelProposal;
import com.vibent.vibentback.bubble.travel.proposal.TravelProposalRepository;
import com.vibent.vibentback.bubble.travel.request.TravelRequest;
import com.vibent.vibentback.bubble.travel.request.TravelRequestRepository;
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
public class AttachedRequestTests extends VibentTest {

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
    public void init() {
        /** User creation **/
        user = new User(UUID.randomUUID().toString(), "conor", "ryan", "cr.sd.sd@gmail.com", "secret", "sel");
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
    public void testAddAttachedRequest() {
        AttachedRequest attachedRequest = new AttachedRequest(travelProposal.getId(), travelRequest.getId());
        repository.save(attachedRequest);
    }
}
