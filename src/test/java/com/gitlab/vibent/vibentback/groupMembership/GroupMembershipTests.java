package com.gitlab.vibent.vibentback.groupMembership;

import com.gitlab.vibent.vibentback.VibentTests;
import com.gitlab.vibent.vibentback.groupMembership.GroupMembership;
import com.gitlab.vibent.vibentback.groupMembership.GroupMembershipRepository;
import com.gitlab.vibent.vibentback.groupT.GroupT;
import com.gitlab.vibent.vibentback.groupT.GroupTRepository;
import com.gitlab.vibent.vibentback.user.User;
import com.gitlab.vibent.vibentback.user.UserRepository;
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
    GroupT group = new GroupT(UUID.randomUUID().toString(), "groupTest");
    User user = new User(UUID.randomUUID().toString(), "Theo", "Joubert", "theojoubertmoureaud@gmail.com","password", "salt");

    @Test
    public void testAddGroupMembership(){
        userRepository.save(user);
        groupTRepository.save(group);
        GroupMembership groupMembership = new GroupMembership(user.getRef(), group.getRef());
        repository.save(groupMembership);
    }

}
