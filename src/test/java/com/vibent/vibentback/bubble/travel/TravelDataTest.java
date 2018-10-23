package com.vibent.vibentback.bubble.travel;

import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.bubble.BubbleType;
import com.vibent.vibentback.bubble.travel.proposal.TravelProposal;
import com.vibent.vibentback.bubble.travel.proposal.TravelProposalRepository;
import com.vibent.vibentback.bubble.travel.request.TravelRequest;
import com.vibent.vibentback.bubble.travel.request.TravelRequestRepository;
import com.vibent.vibentback.event.EventRepository;
import com.vibent.vibentback.group.GroupTRepository;
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
import java.util.HashSet;

@Slf4j
@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class TravelDataTest extends VibentTest {

    @Autowired
    TravelRequestRepository requestRepository;
    @Autowired
    TravelBubbleRepository bubbleRepository;
    @Autowired
    TravelProposalRepository proposalRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    EventRepository eventRepository;
    @Autowired
    GroupTRepository groupTRepository;

    TravelProposal RANDOM_PROPOSAL;
    TravelBubble RANDOM_BUBBLE;
    TravelRequest RANDOM_REQUEST;

    @Before
    public void setUp() {
        super.setUp();

        RANDOM_USER = userRepository.save(RANDOM_USER);
        RANDOM_GROUP = groupTRepository.save(RANDOM_GROUP);
        RANDOM_EVENT = eventRepository.save(RANDOM_EVENT);

        log.info("{}", RANDOM_EVENT.getRef());

        RANDOM_BUBBLE = new TravelBubble();
        RANDOM_BUBBLE.setDeleted(false);
        RANDOM_BUBBLE.setCreator(RANDOM_USER);
        RANDOM_BUBBLE.setEvent(RANDOM_EVENT);
        RANDOM_BUBBLE = bubbleRepository.save(RANDOM_BUBBLE);

        RANDOM_REQUEST = new TravelRequest();
        RANDOM_REQUEST.setProposal(RANDOM_PROPOSAL);
        RANDOM_REQUEST.setUser(RANDOM_USER);
        RANDOM_REQUEST.setDeleted(false);
        RANDOM_REQUEST.setCapacity(1);
        RANDOM_REQUEST.setBubble(RANDOM_BUBBLE);

        RANDOM_PROPOSAL = new TravelProposal();
        RANDOM_PROPOSAL.setDeleted(false);
        RANDOM_PROPOSAL.setBubble(RANDOM_BUBBLE);
        RANDOM_PROPOSAL.setPassByCities("Cities For Tests");
        RANDOM_PROPOSAL.setCapacity(5);
        RANDOM_PROPOSAL.setRequests(new HashSet<TravelRequest>() {{
            add(RANDOM_REQUEST);
        }});
    }

    @Test
    public void testAddTravelBubble() {
        RANDOM_BUBBLE = bubbleRepository.save(RANDOM_BUBBLE);

        Assert.assertNotNull(bubbleRepository.findById(RANDOM_BUBBLE.getId()));
    }

    @Test
    public void testAddTravelProposal() {
        RANDOM_PROPOSAL = proposalRepository.save(RANDOM_PROPOSAL);

        Assert.assertNotNull(proposalRepository.findById(RANDOM_PROPOSAL.getId()));
    }

    @Test
    public void testAddTravelRequest() {
        RANDOM_REQUEST = requestRepository.save(RANDOM_REQUEST);

        Assert.assertNotNull(requestRepository.findById(RANDOM_REQUEST.getId()));
    }
}
