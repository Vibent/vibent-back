package com.gitlab.vibent.vibentback.groupInviteLink;

import com.gitlab.vibent.vibentback.VibentTests;
import com.gitlab.vibent.vibentback.eventParticipation.EventParticipation;
import com.gitlab.vibent.vibentback.eventParticipation.EventParticipationRepository;
import com.gitlab.vibent.vibentback.groupT.GroupT;
import com.gitlab.vibent.vibentback.groupT.GroupTRepository;
import com.gitlab.vibent.vibentback.user.User;
import com.gitlab.vibent.vibentback.user.UserRepository;
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
public class GroupInviteLinkTests extends VibentTests {

    @Autowired
    GroupInviteLinkRepository repository;
    @Autowired
    GroupTRepository groupTRepository;

    GroupT group;


    @Before
    public void init()
    {
        group = new GroupT(UUID.randomUUID().toString(), "groupTest");
        groupTRepository.save(group);
    }
    @Test
    public void testAddGroupInviteLink(){
        GroupInviteLink eventParticipation = new GroupInviteLink(group.getRef(), "hash",new Date());
        repository.save(eventParticipation);
    }

}
