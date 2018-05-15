package com.vibent.vibentback.bubble.alimentation;

import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.api.alimentation.AlimentationBubbleRequest;
import com.vibent.vibentback.api.event.EventRequest;
import com.vibent.vibentback.api.event.EventUpdateRequest;
import com.vibent.vibentback.bubble.BubbleType;
import com.vibent.vibentback.bubble.alimentation.bring.AlimentationBring;
import com.vibent.vibentback.bubble.alimentation.entry.AlimentationEntry;
import com.vibent.vibentback.error.VibentException;
import com.vibent.vibentback.event.Event;
import com.vibent.vibentback.event.EventController;
import com.vibent.vibentback.event.EventRepository;
import com.vibent.vibentback.eventParticipation.EventParticipation;
import com.vibent.vibentback.eventParticipation.EventParticipationRepository;
import com.vibent.vibentback.groupT.GroupTRepository;
import com.vibent.vibentback.user.UserRepository;
import com.vibent.vibentback.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.when;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class AlimentationInternalTest extends VibentTest {


    @Autowired
    @InjectMocks
    private AlimentationController controller;

    @MockBean
    AbstractUserDetailsAuthenticationProvider interceptor;
    @MockBean
    UserRepository userRepository;
    @MockBean
    AlimentationBubbleRepository bubbleRepository;

    private AlimentationEntry RANDOM_ENTRY;
    private AlimentationBubble RANDOM_BUBBLE;
    private AlimentationBring RANDOM_BRING;
    private AlimentationBubbleRequest bubbleRequest;

    @Before
    public void setUp(){
        super.setUp();
        MockitoAnnotations.initMocks(this);

        RANDOM_BUBBLE = new AlimentationBubble();
        RANDOM_BUBBLE.setDeleted(false);
        RANDOM_BUBBLE.setCreator(RANDOM_USER);
        RANDOM_BUBBLE.setEvent(RANDOM_EVENT);
        RANDOM_BUBBLE.setType(BubbleType.AlimentationBubble);
        RANDOM_BUBBLE.setEntries(new HashSet<AlimentationEntry>() {{
            add(RANDOM_ENTRY);
        }});

        RANDOM_ENTRY = new AlimentationEntry();
        RANDOM_ENTRY.setDeleted(false);
        RANDOM_ENTRY.setBubble(RANDOM_BUBBLE);
        RANDOM_ENTRY.setName("coke");
        RANDOM_ENTRY.setTotalRequested(100);
        RANDOM_ENTRY.setType(AlimentationEntry.Type.FOOD);
        RANDOM_ENTRY.setBrings(new HashSet<AlimentationBring>() {{
            add(RANDOM_BRING);
        }});

        RANDOM_BRING = new AlimentationBring();
        RANDOM_BRING.setEntry(RANDOM_ENTRY);
        RANDOM_BRING.setUser(RANDOM_USER);
        RANDOM_BRING.setDeleted(false);
        RANDOM_BRING.setQuantity(5);

        bubbleRequest = new AlimentationBubbleRequest();
        bubbleRequest.setEventRef(RANDOM_EVENT.getRef());

        when(userRepository.findByUsername(RANDOM_USER.getUsername())).thenReturn(Optional.of(RANDOM_USER));
        when(interceptor.authenticate(AUTHENTICATION)).thenReturn(AUTHENTICATION);
    }


    @Test
    public void createBubbleTest(){
        AlimentationBubble bubble = controller.createBubble(bubbleRequest);
        Assert.assertEquals(bubble.getEventRef(), bubbleRequest.getEventRef());
    }


    /*
    @Test
    public void getEvent(){
        Event event = controller.getEvent(RANDOM_EVENT.getRef());
        Assert.assertEquals(RANDOM_EVENT.getRef(), event.getRef());
        Assert.assertEquals(RANDOM_EVENT.getDescription(), event.getDescription());
    }

    @Test
    public void addEvent(){
        Event event = controller.createEvent(RANDOM_EVENT_REQUEST);
        Assert.assertEquals(RANDOM_EVENT.getRef(), event.getRef());
        Assert.assertEquals(RANDOM_EVENT.getDescription(), event.getDescription());
    }

    @Test(expected = VibentException.class)
    public void addEventWithInvalidEndDate(){
        RANDOM_EVENT_REQUEST.setEndDate(getFutureDate(2));
        controller.createEvent(RANDOM_EVENT_REQUEST);
    }

    @Test(expected = VibentException.class)
    public void addEventWithInvalidStartDate(){
        RANDOM_EVENT_REQUEST.setStartDate(getPastDate(2));
        controller.createEvent(RANDOM_EVENT_REQUEST);
    }

    @Test
    public void updateEvent(){
        Event event = controller.updateEvent(RANDOM_EVENT.getRef(), RANDOM_EVENT_UPDATE_REQUEST);
        Assert.assertEquals(event, RANDOM_EVENT);
        Assert.assertEquals(RANDOM_EVENT_UPDATE_REQUEST.getDescription(), event.getDescription());
    }

    @Test
    public void deleteEvent(){
        controller.deleteEvent(RANDOM_EVENT.getRef());
    } */
}
