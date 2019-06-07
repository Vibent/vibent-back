package com.vibent.vibentback.event;

import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.common.error.VibentError;
import com.vibent.vibentback.common.error.VibentException;
import com.vibent.vibentback.group.GroupTRepository;
import com.vibent.vibentback.user.UserRepository;
import org.junit.Assert;
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
    @Autowired
    UserRepository userRepository;

    @Before
    public void setUp() {
        super.setUp();
        RANDOM_GROUP = groupRepository.save(RANDOM_GROUP);
        RANDOM_USER = userRepository.save(RANDOM_USER);
        RANDOM_EVENT = repository.save(RANDOM_EVENT);
    }

    @Test
    public void testAddEvent() {
        Event event = new Event();
        event.setRef(UUID.randomUUID().toString());
        event.setGroup(RANDOM_GROUP);
        event.setTitle("test");
        event.setCreator(RANDOM_USER);
        event.setDescription("test");
        event.setStartDate(getFutureDate(5));
        event.setEndDate(getFutureDate(8));
        Assert.assertNotNull(event.getRef());
        repository.save(event);
    }

    @Test
    public void testGetEvent() {
        Event event = repository.findByRef(RANDOM_EVENT.getRef()).orElseThrow(() -> new VibentException(VibentError.EVENT_NOT_FOUND));
        Assert.assertNotNull(event.getRef());
    }

    @Test
    public void testDeleteEvent() {
        Integer deletedAmount = repository.deleteByRef(RANDOM_EVENT.getRef());
        Assert.assertEquals(1, deletedAmount.intValue());
    }
}
