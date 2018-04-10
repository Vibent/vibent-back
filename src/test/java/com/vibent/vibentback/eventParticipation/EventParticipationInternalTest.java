package com.vibent.vibentback.eventParticipation;

import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.api.eventParticipation.UpdateEventParticipationRequest;
import com.vibent.vibentback.event.Event;
import com.vibent.vibentback.event.EventRepository;
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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.when;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class EventParticipationInternalTest extends VibentTest {

    @Autowired
    @InjectMocks
    private EventParticipationController controller;

    @MockBean
    private EventParticipationRepository repository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private GroupTRepository groupTRepository;
    @MockBean
    private EventRepository eventRepository;

    private EventParticipation RANDOM_PARTICIPATION;
    private UpdateEventParticipationRequest RANDOM_EVENT_PARTICIPATION_UPDATE_REQUEST;

    @Before
    public void setUp(){
        super.setUp();
        MockitoAnnotations.initMocks(this);

        RANDOM_PARTICIPATION = new EventParticipation();
        RANDOM_PARTICIPATION.setId(666L);
        RANDOM_PARTICIPATION.setUser(RANDOM_USER);
        RANDOM_PARTICIPATION.setEvent(RANDOM_EVENT);
        RANDOM_PARTICIPATION.setVisible(true);
        RANDOM_PARTICIPATION.setAnswer(EventParticipation.Answer.UNANSWERED);

        EventParticipation OTHER_RANDOM_PARTICIPATION = new EventParticipation();
        OTHER_RANDOM_PARTICIPATION.setId(777L);
        OTHER_RANDOM_PARTICIPATION.setUser(RANDOM_USER);
        OTHER_RANDOM_PARTICIPATION.setEvent(RANDOM_EVENT);
        OTHER_RANDOM_PARTICIPATION.setVisible(true);
        OTHER_RANDOM_PARTICIPATION.setAnswer(EventParticipation.Answer.UNANSWERED);

        RANDOM_EVENT_PARTICIPATION_UPDATE_REQUEST = new UpdateEventParticipationRequest();
        RANDOM_EVENT_PARTICIPATION_UPDATE_REQUEST.setAnswer(EventParticipation.Answer.NO);
        RANDOM_EVENT_PARTICIPATION_UPDATE_REQUEST.setIsVisible(false);

        Set<EventParticipation> participations = new HashSet<>(Arrays.asList(RANDOM_PARTICIPATION, OTHER_RANDOM_PARTICIPATION));

        when(repository.findByEvent(RANDOM_EVENT)).thenReturn(participations);
        when(repository.findByUser(RANDOM_USER)).thenReturn(participations);
        when(repository.save(RANDOM_PARTICIPATION)).thenReturn(RANDOM_PARTICIPATION);
        when(repository.findById(RANDOM_PARTICIPATION.getId())).thenReturn(Optional.ofNullable(RANDOM_PARTICIPATION));
        when(userRepository.findByRef(RANDOM_USER.getRef())).thenReturn(Optional.ofNullable(RANDOM_USER));
        when(groupTRepository.findByRef(RANDOM_GROUP.getRef())).thenReturn(Optional.ofNullable(RANDOM_GROUP));
        when(eventRepository.findByRef(RANDOM_EVENT.getRef())).thenReturn(Optional.ofNullable(RANDOM_EVENT));
    }

    @Test
    public void getEventParticipations(){
        Set<EventParticipation> participations = controller.getEventParticipations(RANDOM_EVENT.getRef());
        Assert.assertEquals(2, participations.size());
    }

    @Test
    public void getUserParticipations(){
        Set<EventParticipation> participations = controller.getUserParticipations(RANDOM_USER.getRef());
        Assert.assertEquals(2, participations.size());
    }

    @Test
    public void updateEventParticipation(){
        EventParticipation participation = controller.updateEventParticipation(RANDOM_PARTICIPATION.getId(), RANDOM_EVENT_PARTICIPATION_UPDATE_REQUEST);
        Assert.assertEquals(EventParticipation.Answer.NO, participation.getAnswer());
    }
}
