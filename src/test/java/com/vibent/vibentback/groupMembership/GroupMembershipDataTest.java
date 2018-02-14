package com.vibent.vibentback.groupMembership;

import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.groupT.GroupT;
import com.vibent.vibentback.groupT.GroupTRepository;
import com.vibent.vibentback.user.User;
import com.vibent.vibentback.user.UserRepository;
import com.vibent.vibentback.groupT.GroupT;
import com.vibent.vibentback.groupT.GroupTRepository;
import com.vibent.vibentback.user.User;
import com.vibent.vibentback.user.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.UUID;


@RunWith(SpringRunner.class)
@SpringBootTest
public class GroupMembershipDataTest extends VibentTest {

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
