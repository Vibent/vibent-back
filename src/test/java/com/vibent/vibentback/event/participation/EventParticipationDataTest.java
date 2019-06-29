package com.vibent.vibentback.event.participation;

import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.event.EventRepository;
import com.vibent.vibentback.user.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class EventParticipationDataTest extends VibentTest {

    @Autowired
    EventParticipationRepository repository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    EventRepository eventRepository;

    private EventParticipation RANDOM_EVENT_PARTICIPATION;

    @Before
    public void setUp() {
        super.setUp();
        RANDOM_USER = userRepository.save(RANDOM_USER);
        RANDOM_EVENT = eventRepository.save(RANDOM_EVENT);

        RANDOM_EVENT_PARTICIPATION = new EventParticipation();
        RANDOM_EVENT_PARTICIPATION.setAnswer(EventParticipation.Answer.YES);
        RANDOM_EVENT_PARTICIPATION.setEvent(RANDOM_EVENT);
        RANDOM_EVENT_PARTICIPATION.setId(666L);
        RANDOM_EVENT_PARTICIPATION.setUser(RANDOM_USER);
        RANDOM_EVENT_PARTICIPATION.setVisible(true);
    }

    @Test
    public void testAddEventParticipation() {
        RANDOM_EVENT_PARTICIPATION = repository.save(RANDOM_EVENT_PARTICIPATION);
        Assert.assertNotNull(RANDOM_EVENT_PARTICIPATION);
    }

    @Test
    public void testUpdateEventParticipation() {
        RANDOM_EVENT_PARTICIPATION = repository.save(RANDOM_EVENT_PARTICIPATION);
        RANDOM_EVENT_PARTICIPATION.setVisible(false);
        RANDOM_EVENT_PARTICIPATION.setAnswer(EventParticipation.Answer.MAYBE);

        EventParticipation participation = repository.save(RANDOM_EVENT_PARTICIPATION);
        Assert.assertEquals(EventParticipation.Answer.MAYBE, participation.getAnswer());
        Assert.assertEquals(false, participation.isVisible());
    }
}
