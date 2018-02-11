package com.gitlab.vibent.vibentback.event;

import com.gitlab.vibent.vibentback.VibentTests;
import com.gitlab.vibent.vibentback.groupT.GroupT;
import com.gitlab.vibent.vibentback.groupT.GroupTRepository;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EventTests extends VibentTests {

    @Autowired
    EventRepository repository;
    @Autowired
    GroupTRepository groupRepository;
    GroupT group;

    @Before
    public void init()
    {
        group = new GroupT(UUID.randomUUID().toString(), "groupTest");
        groupRepository.save(group);
    }

    @Test
    public void testAddEvent(){
        Event event = new Event(UUID.randomUUID().toString(), group.getRef(), "eventTest", "description",new Date(), new Date());
        repository.save(event);
    }

}
