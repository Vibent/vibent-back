package com.gitlab.vibent.vibentback.bubble;

import com.gitlab.vibent.vibentback.VibentTests;
import com.gitlab.vibent.vibentback.bubble.travel.TravelBubble;
import com.gitlab.vibent.vibentback.bubble.travel.TravelBubbleRepository;
import com.gitlab.vibent.vibentback.bubble.travel.request.TravelRequest;
import com.gitlab.vibent.vibentback.bubble.travel.request.TravelRequestRepository;
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
public class TravelRequestTests extends VibentTests {

    @Autowired
    TravelRequestRepository repository;

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
    public void testAddTravelRequest(){
        TravelRequest TravelRequest = new TravelRequest(travelBubble.getId(), user.getRef());
        repository.save(TravelRequest);
    }

}
