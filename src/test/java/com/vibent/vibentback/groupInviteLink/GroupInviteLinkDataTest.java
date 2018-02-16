package com.vibent.vibentback.groupInviteLink;

import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.groupT.GroupT;
import com.vibent.vibentback.groupT.GroupTRepository;
import com.vibent.vibentback.groupT.GroupT;
import com.vibent.vibentback.groupT.GroupTRepository;
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
public class GroupInviteLinkDataTest extends VibentTest {

    @Autowired
    GroupInviteLinkRepository repository;
    @Autowired
    GroupTRepository groupTRepository;

    GroupT group;


    @Before
    public void setUp()
    {
        super.setUp();
        group = new GroupT(UUID.randomUUID().toString(), "groupTest");
        groupTRepository.save(group);
    }
    @Test
    public void testAddGroupInviteLink(){
        GroupInviteLink eventParticipation = new GroupInviteLink(group.getRef(), "hash",new Date());
        repository.save(eventParticipation);
    }

}
