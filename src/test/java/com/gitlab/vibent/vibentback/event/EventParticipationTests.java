package com.gitlab.vibent.vibentback.event;

import com.gitlab.vibent.vibentback.VibentTests;
import com.gitlab.vibent.vibentback.event.Event.Event;
import com.gitlab.vibent.vibentback.event.Event.EventRepository;
import com.gitlab.vibent.vibentback.event.EventParticipation.EventParticipation;
import com.gitlab.vibent.vibentback.event.EventParticipation.EventParticipationRepository;
import com.gitlab.vibent.vibentback.groupT.GroupT;
import com.gitlab.vibent.vibentback.user.User;
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
public class EventParticipationTests extends VibentTests {

    @Autowired
    EventParticipationRepository repository;
    GroupT group = new GroupT(UUID.randomUUID().toString(), "groupTest", false);
    User user = new User(UUID.randomUUID().toString(), "Theo", "Joubert", "theojoubertmoureaud@gmail.com","password", "salt");
    
    @Test
    public void testAddEventParticipation(){
        EventParticipation eventParticipation = new EventParticipation(user.getRef(), group.getRef(),"Yes", true);
        repository.save(eventParticipation);
    }

}
