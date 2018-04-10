package com.vibent.vibentback.event;

import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.common.ObjectUpdater;
import com.vibent.vibentback.error.VibentError;
import com.vibent.vibentback.error.VibentException;
import com.vibent.vibentback.groupT.GroupTRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class EventDataTest extends VibentTest {

    @Autowired
    EventRepository repository;
    @Autowired
    GroupTRepository groupRepository;

    @Before
    public void setUp() {
        super.setUp();
        groupRepository.save(RANDOM_GROUP);
        repository.save(RANDOM_EVENT);
    }

    @After
    public void tearDown() {
        repository.deleteByRef(RANDOM_EVENT.getRef());
        groupRepository.deleteByRef(RANDOM_GROUP.getRef());
    }

    @Test
    public void testAddEvent() {
        Event event = new Event(UUID.randomUUID().toString(), RANDOM_GROUP.getRef(), "eventTest", "description",
                new GregorianCalendar(1970, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(1970, Calendar.JANUARY, 2).getTime());
        Assert.assertNotNull(event.getRef());
        repository.save(event);

        // Clean up
        repository.delete(event);
    }

    @Test
    public void testGetEvent() {
        Event event = repository.findByRef(RANDOM_EVENT.getRef()).orElseThrow(() -> new VibentException(VibentError.EVENT_NOT_FOUND));
        ;
        Assert.assertNotNull(event.getRef());
    }

    @Test
    public void testDeleteEvent() {
        Integer deletedAmount = repository.deleteByRef(RANDOM_EVENT.getRef());
        Assert.assertEquals(1, deletedAmount.intValue());
    }

    @Test
    public void testUpdateEvent() {
        // Set up
        Event newEvent = new Event();
        newEvent.setRef("newRefShouldNotUpdate");
        newEvent.setId(-1L);
        newEvent.setTitle("NewTitleShouldUpdate");
        Event old = repository.findByRef(RANDOM_EVENT.getRef()).orElseThrow(() -> new VibentException(VibentError.EVENT_NOT_FOUND));
        String oldDescriptionShouldNotUpdate = old.getDescription();

        // To test
        ObjectUpdater.updateProperties(newEvent, old);

        Event checkEvent = repository.save(old);
        Assert.assertEquals("NewTitleShouldUpdate", checkEvent.getTitle());
        Assert.assertNotEquals(-1L, checkEvent.getId().longValue());
        Assert.assertNotEquals("newRefShouldNotUpdate", checkEvent.getRef());
        Assert.assertEquals(oldDescriptionShouldNotUpdate, checkEvent.getDescription());
    }

}
