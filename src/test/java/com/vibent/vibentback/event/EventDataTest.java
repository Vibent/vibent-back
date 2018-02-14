package com.vibent.vibentback.event;

import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.common.ObjectUpdater;
import com.vibent.vibentback.groupT.GroupT;
import com.vibent.vibentback.groupT.GroupTRepository;
import com.vibent.vibentback.user.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EventDataTest extends VibentTest {

    @Autowired
    EventRepository repository;
    @Autowired
    GroupTRepository groupRepository;
    GroupT group;

    @Before
    public void setUp()
    {
        group = new GroupT(UUID.randomUUID().toString(), "groupTest");
        groupRepository.save(RANDOM_GROUP);
        groupRepository.save(group);
        repository.save(RANDOM_EVENT);
    }

    @After
    public void tearDown() {
        repository.deleteByRef(RANDOM_EVENT.getRef());
        groupRepository.deleteByRef(RANDOM_GROUP.getRef());
        groupRepository.deleteByRef(group.getRef());
    }

    @Test
    public void testAddEvent(){
        Event event = new Event(UUID.randomUUID().toString(), group.getRef(), "eventTest", "description",new Date(), new Date());
        Assert.assertNotNull(event.getRef());
        repository.save(event);

        // Clean up
        repository.delete(event);
    }

    @Test
    public void testGetEvent() {
        Event event = repository.findByRef(RANDOM_EVENT.getRef());
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
        Event old = repository.findByRef(RANDOM_EVENT.getRef());
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
