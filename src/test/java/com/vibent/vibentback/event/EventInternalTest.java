package com.vibent.vibentback.event;

import com.vibent.vibentback.VibentTest;
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

    @Before
    public void setUp(){
        super.setUp();
        MockitoAnnotations.initMocks(this);
        when(eventRepository.findByRef(RANDOM_EVENT.getRef())).thenReturn(RANDOM_EVENT);
        when(eventRepository.save(RANDOM_EVENT)).thenReturn(RANDOM_EVENT);
        when(eventRepository.deleteByRef(RANDOM_EVENT.getRef())).thenReturn(1);
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
