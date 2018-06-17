package com.vibent.vibentback.event;

import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.common.error.VibentError;
import com.vibent.vibentback.common.error.VibentException;
import com.vibent.vibentback.group.GroupTRepository;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
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
        RANDOM_GROUP = groupRepository.save(RANDOM_GROUP);
        RANDOM_EVENT = repository.save(RANDOM_EVENT);
    }

    @Test
    public void testAddEvent() {
        Event event = new Event(UUID.randomUUID().toString(), RANDOM_GROUP, "eventTest", "description",
                getFutureDate(4));
        event.setEndDate(getFutureDate(8));
        Assert.assertNotNull(event.getRef());
        repository.save(event);
    }

    @Test
    public void testGetEvent() {
        Assume.assumeFalse(env.acceptsProfiles("gitlab-ci"));
        Event event = repository.findByRef(RANDOM_EVENT.getRef()).orElseThrow(() -> new VibentException(VibentError.EVENT_NOT_FOUND));
        Assert.assertNotNull(event.getRef());
    }

    @Test
    public void testDeleteEvent() {
        Assume.assumeFalse(env.acceptsProfiles("gitlab-ci"));
        Integer deletedAmount = repository.deleteByRef(RANDOM_EVENT.getRef());
        Assert.assertEquals(1, deletedAmount.intValue());
    }
}
