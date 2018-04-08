package com.vibent.vibentback.event;

import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.eventParticipation.EventParticipation;
import com.vibent.vibentback.eventParticipation.EventParticipationRepository;
import com.vibent.vibentback.groupT.GroupTRepository;
import com.vibent.vibentback.user.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.mockito.Mockito.when;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class EventInternalTest extends VibentTest {

    @Autowired
    @InjectMocks
    private EventController controller;

    @MockBean
    private EventRepository eventRepository;

    @MockBean
    EventParticipationRepository eventParticipationRepository;

    @MockBean
    GroupTRepository groupTRepository;

    @Before
    public void setUp(){
        super.setUp();
        MockitoAnnotations.initMocks(this);

        EventParticipation participation = new EventParticipation();
        participation.setUser(RANDOM_USER);
        participation.setEvent(RANDOM_EVENT);
        participation.setVisible(true);
        participation.setAnswer(EventParticipation.Answer.UNANSWERED);

        when(eventRepository.findByRef(RANDOM_EVENT.getRef())).thenReturn(Optional.of(RANDOM_EVENT));
        when(eventRepository.save(RANDOM_EVENT)).thenReturn(RANDOM_EVENT);
        when(eventRepository.deleteByRef(RANDOM_EVENT.getRef())).thenReturn(1);
        when(eventParticipationRepository.save(participation)).thenReturn(participation);
        when(groupTRepository.findByRef(RANDOM_GROUP.getRef())).thenReturn(Optional.ofNullable(RANDOM_GROUP));
    }

    @Test
    public void getEvent(){
        Event event = controller.getEvent(RANDOM_EVENT.getRef());
        Assert.assertEquals(RANDOM_EVENT.getRef(), event.getRef());
    }

    @Test
    public void addEvent(){
        Event event = controller.createEvent(RANDOM_EVENT);
        Assert.assertEquals(RANDOM_EVENT.getRef(), event.getRef());
    }

    @Test
    public void updateEvent(){
        Event event = RANDOM_EVENT;
        event.setTitle("NewTitle");
        controller.updateEvent(RANDOM_EVENT.getRef(), event);
        Assert.assertEquals(event, RANDOM_EVENT);
    }

    @Test
    public void deleteEvent(){
        controller.deleteEvent(RANDOM_EVENT.getRef());
    }
}
