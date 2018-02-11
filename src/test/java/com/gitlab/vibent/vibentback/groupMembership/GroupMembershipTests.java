package com.gitlab.vibent.vibentback.groupMembership;

import com.gitlab.vibent.vibentback.VibentTests;
import com.gitlab.vibent.vibentback.groupMembership.GroupMembership;
import com.gitlab.vibent.vibentback.groupMembership.GroupMembershipRepository;
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
import java.util.UUID;


@RunWith(SpringRunner.class)
@SpringBootTest
public class GroupMembershipTests extends VibentTests {

    @Autowired
    GroupMembershipRepository repository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    GroupTRepository groupTRepository;

    GroupT group;
    User user;

    @Before
    public void init()
    {
        group = new GroupT(UUID.randomUUID().toString(), "groupTest");
        user = new User(UUID.randomUUID().toString(), "Theo", "Joubert", "theojoubertmoureaud@gmail.com","password", "salt");
        userRepository.save(user);
        groupTRepository.save(group);
    }
    @Test
    public void testAddGroupMembership(){
        GroupMembership groupMembership = new GroupMembership(user.getRef(), group.getRef());
        repository.save(groupMembership);
    }

}
