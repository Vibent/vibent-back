package com.vibent.vibentback.event;

import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.api.event.EventRequest;
import com.vibent.vibentback.api.event.EventUpdateRequest;
import com.vibent.vibentback.error.VibentException;
import com.vibent.vibentback.eventParticipation.EventParticipation;
import com.vibent.vibentback.eventParticipation.EventParticipationRepository;
import com.vibent.vibentback.groupT.GroupTRepository;
import com.vibent.vibentback.user.UserRepository;
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
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.when;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class EventInternalTest extends VibentTest {

    @Autowired
    @InjectMocks
    private EventController controller;

    @MockBean
    UserRepository userRepository;

    @MockBean
    private EventRepository eventRepository;

    @MockBean
    EventParticipationRepository eventParticipationRepository;

    @MockBean
    GroupTRepository groupTRepository;

    private EventRequest RANDOM_EVENT_REQUEST;
    private EventUpdateRequest RANDOM_EVENT_UPDATE_REQUEST;

    @Before
    public void setUp(){
        super.setUp();
        MockitoAnnotations.initMocks(this);

        EventParticipation participation = new EventParticipation();
        participation.setUser(RANDOM_USER);
        participation.setEvent(RANDOM_EVENT);
        participation.setVisible(true);
        participation.setAnswer(EventParticipation.Answer.UNANSWERED);

        RANDOM_EVENT_REQUEST = new EventRequest();
        RANDOM_EVENT_REQUEST.setTitle("Random title");
        RANDOM_EVENT_REQUEST.setDescription("Random descipt.");
        RANDOM_EVENT_REQUEST.setStartDate(getFutureDate(5));
        RANDOM_EVENT_REQUEST.setEndDate(getFutureDate(10));
        RANDOM_EVENT_REQUEST.setGroupRef(RANDOM_GROUP.getRef());

        RANDOM_EVENT_UPDATE_REQUEST = new EventUpdateRequest();
        RANDOM_EVENT_UPDATE_REQUEST.setDescription("New description");

        Event RANDOM_UPDATE_EVENT = RANDOM_EVENT = new Event(RANDOM_EVENT.getRef(), RANDOM_EVENT.getGroup(), RANDOM_EVENT.getTitle(), RANDOM_EVENT_UPDATE_REQUEST.getDescription(), RANDOM_EVENT.getStartDate());

        when(userRepository.findByUsername(RANDOM_USER.getUsername())).thenReturn(Optional.of(RANDOM_USER));
        when(eventRepository.findByRef(RANDOM_EVENT.getRef())).thenReturn(Optional.of(RANDOM_EVENT));
        when(eventRepository.save(RANDOM_UPDATE_EVENT)).thenReturn(RANDOM_UPDATE_EVENT);
        when(eventRepository.save(RANDOM_EVENT)).thenReturn(RANDOM_EVENT);
        when(eventRepository.deleteByRef(RANDOM_EVENT.getRef())).thenReturn(1);
        when(eventParticipationRepository.save(participation)).thenReturn(participation);
        when(groupTRepository.findByRef(RANDOM_GROUP.getRef())).thenReturn(Optional.ofNullable(RANDOM_GROUP));
    }

    @Test
    public void getConnectedUserEvents(){
        Set<Event> events = controller.getConnectedUserEvents();
        log.info(String.valueOf(events.size()));
    }


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
    }
}
