package com.vibent.vibentback.bubble.travel;

import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.bubble.travel.TravelBubble;
import com.vibent.vibentback.bubble.travel.TravelBubbleRepository;
import com.vibent.vibentback.bubble.travel.request.TravelRequest;
import com.vibent.vibentback.bubble.travel.request.TravelRequestRepository;
import com.vibent.vibentback.user.User;
import com.vibent.vibentback.user.UserRepository;
import com.vibent.vibentback.bubble.travel.TravelBubble;
import com.vibent.vibentback.bubble.travel.TravelBubbleRepository;
import com.vibent.vibentback.bubble.travel.request.TravelRequest;
import com.vibent.vibentback.bubble.travel.request.TravelRequestRepository;
import com.vibent.vibentback.user.User;
import com.vibent.vibentback.user.UserRepository;
import javax.transaction.Transactional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class TravelRequestDataTest extends VibentTest {

    @Autowired
    TravelRequestRepository repository;

    @Autowired
    TravelBubbleRepository travelRepository;

    @Autowired
    UserRepository userRepository;

    TravelBubble travelBubble;

    @Before
    public void setUp()
    {
        super.setUp();
        userRepository.save(RANDOM_USER);
        travelBubble = new TravelBubble();
        travelRepository.save(travelBubble);
    }
    @Test
    public void testAddTravelRequest(){
        TravelRequest TravelRequest = new TravelRequest(travelBubble.getId(), RANDOM_USER.getRef());
        repository.save(TravelRequest);
    }

}
