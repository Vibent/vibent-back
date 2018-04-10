package com.vibent.vibentback.groupLinkToUserTest;

import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.error.VibentError;
import com.vibent.vibentback.error.VibentException;
import com.vibent.vibentback.groupT.GroupT;
import com.vibent.vibentback.groupT.GroupTRepository;
import com.vibent.vibentback.user.User;
import com.vibent.vibentback.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.HashSet;


@Slf4j
@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class GroupMembershipDataTest extends VibentTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    GroupTRepository groupTRepository;

    @Before
    public void setUp() {
        super.setUp();
        RANDOM_USER = userRepository.save(RANDOM_USER);
        RANDOM_GROUP = groupTRepository.save(RANDOM_GROUP);
    }

    // Memberships
    @Test
    public void testAddGroupMembershipFromGroupSide() {
        RANDOM_GROUP.addMember(RANDOM_USER);

        RANDOM_GROUP = groupTRepository.save(RANDOM_GROUP);

        GroupT group = groupTRepository.findById(RANDOM_GROUP.getId()).orElseThrow(() -> new VibentException(VibentError.GROUP_NOT_FOUND));
        Assert.assertNotEquals(0, group.getMembers().size());

        User user = userRepository.findById(RANDOM_USER.getId()).orElseThrow(() -> new VibentException(VibentError.USER_NOT_FOUND));
        Assert.assertNotEquals(0, user.getMemberships().size());
    }

    @Test
    public void testAddGroupMembershipFromUserSide() {
        RANDOM_USER.addMembership(RANDOM_GROUP);

        RANDOM_USER = userRepository.save(RANDOM_USER);

        GroupT group = groupTRepository.findById(RANDOM_GROUP.getId()).orElseThrow(() -> new VibentException(VibentError.GROUP_NOT_FOUND));
        Assert.assertNotEquals(0, group.getMembers().size());

        User user = userRepository.findById(RANDOM_USER.getId()).orElseThrow(() -> new VibentException(VibentError.USER_NOT_FOUND));
        Assert.assertNotEquals(0, user.getMemberships().size());
    }

    @Test
    public void deleteGroupMembershipFromUserShouldNotDeleteGroup() {
        RANDOM_USER.addMembership(RANDOM_GROUP);
        RANDOM_USER = userRepository.save(RANDOM_USER);

        RANDOM_USER.setMemberships(new HashSet<>());
        RANDOM_USER = userRepository.save(RANDOM_USER);

        // Should not throw exception
        groupTRepository.findById(RANDOM_GROUP.getId()).orElseThrow(() -> new VibentException(VibentError.GROUP_NOT_FOUND));

        // Should be out of all groups
        Assert.assertEquals(0, RANDOM_USER.getMemberships().size());
    }

    @Test
    public void deleteUserMembershipFromGroupShouldNotDeleteUser() {
        RANDOM_GROUP.addMember(RANDOM_USER);
        RANDOM_GROUP = groupTRepository.save(RANDOM_GROUP);

        RANDOM_GROUP.setMembers(new HashSet<>());
        RANDOM_GROUP = groupTRepository.save(RANDOM_GROUP);

        // Should not throw exception
        userRepository.findById(RANDOM_USER.getId()).orElseThrow(() -> new VibentException(VibentError.GROUP_NOT_FOUND));

        // Should have to members
        Assert.assertEquals(0, RANDOM_GROUP.getMembers().size());
    }

    // Adminships
    @Test
    public void testAddGroupAdminshipFromGroupSide() {
        RANDOM_GROUP.addAdmin(RANDOM_USER);

        RANDOM_GROUP = groupTRepository.save(RANDOM_GROUP);

        GroupT group = groupTRepository.findById(RANDOM_GROUP.getId()).orElseThrow(() -> new VibentException(VibentError.GROUP_NOT_FOUND));
        Assert.assertNotEquals(0, group.getAdmins().size());

        User user = userRepository.findById(RANDOM_USER.getId()).orElseThrow(() -> new VibentException(VibentError.USER_NOT_FOUND));
        Assert.assertNotEquals(0, user.getAdminships().size());
    }

    @Test
    public void testAddGroupAdminshipFromUserSide() {
        RANDOM_USER.addAdminship(RANDOM_GROUP);

        RANDOM_USER = userRepository.save(RANDOM_USER);

        GroupT group = groupTRepository.findById(RANDOM_GROUP.getId()).orElseThrow(() -> new VibentException(VibentError.GROUP_NOT_FOUND));
        Assert.assertNotEquals(0, group.getAdmins().size());

        User user = userRepository.findById(RANDOM_USER.getId()).orElseThrow(() -> new VibentException(VibentError.USER_NOT_FOUND));
        Assert.assertNotEquals(0, user.getAdminships().size());
    }

    @Test
    public void deleteGroupAdminshipFromUserShouldNotDeleteGroup() {
        RANDOM_USER.addAdminship(RANDOM_GROUP);
        RANDOM_USER = userRepository.save(RANDOM_USER);

        RANDOM_USER.setAdminships(new HashSet<>());
        RANDOM_USER = userRepository.save(RANDOM_USER);

        // Should not throw exception
        groupTRepository.findById(RANDOM_GROUP.getId()).orElseThrow(() -> new VibentException(VibentError.GROUP_NOT_FOUND));

        // Should be out of all groups
        Assert.assertEquals(0, RANDOM_USER.getAdminships().size());
    }

    @Test
    public void deleteUserAdminshipFromGroupShouldNotDeleteUser() {
        RANDOM_GROUP.addAdmin(RANDOM_USER);
        RANDOM_GROUP = groupTRepository.save(RANDOM_GROUP);

        RANDOM_GROUP.setAdmins(new HashSet<>());
        RANDOM_GROUP = groupTRepository.save(RANDOM_GROUP);

        // Should not throw exception
        userRepository.findById(RANDOM_USER.getId()).orElseThrow(() -> new VibentException(VibentError.GROUP_NOT_FOUND));

        // Should have to admins
        Assert.assertEquals(0, RANDOM_GROUP.getAdmins().size());
    }

    // Inviteships
    @Test
    public void testAddGroupInviteshipFromGroupSide() {
        RANDOM_GROUP.addInvite(RANDOM_USER);

        RANDOM_GROUP = groupTRepository.save(RANDOM_GROUP);

        GroupT group = groupTRepository.findById(RANDOM_GROUP.getId()).orElseThrow(() -> new VibentException(VibentError.GROUP_NOT_FOUND));
        Assert.assertNotEquals(0, group.getInvites().size());

        User user = userRepository.findById(RANDOM_USER.getId()).orElseThrow(() -> new VibentException(VibentError.USER_NOT_FOUND));
        Assert.assertNotEquals(0, user.getInviteships().size());
    }

    @Test
    public void testAddGroupInviteshipFromUserSide() {
        RANDOM_USER.addInviteship(RANDOM_GROUP);

        RANDOM_USER = userRepository.save(RANDOM_USER);

        GroupT group = groupTRepository.findById(RANDOM_GROUP.getId()).orElseThrow(() -> new VibentException(VibentError.GROUP_NOT_FOUND));
        Assert.assertNotEquals(0, group.getInvites().size());

        User user = userRepository.findById(RANDOM_USER.getId()).orElseThrow(() -> new VibentException(VibentError.USER_NOT_FOUND));
        Assert.assertNotEquals(0, user.getInviteships().size());
    }

    @Test
    public void deleteGroupInviteshipFromUserShouldNotDeleteGroup() {
        RANDOM_USER.addInviteship(RANDOM_GROUP);
        RANDOM_USER = userRepository.save(RANDOM_USER);

        RANDOM_USER.setInviteships(new HashSet<>());
        RANDOM_USER = userRepository.save(RANDOM_USER);

        // Should not throw exception
        groupTRepository.findById(RANDOM_GROUP.getId()).orElseThrow(() -> new VibentException(VibentError.GROUP_NOT_FOUND));

        // Should be out of all groups
        Assert.assertEquals(0, RANDOM_USER.getInviteships().size());
    }

    @Test
    public void deleteUserInviteshipFromGroupShouldNotDeleteUser() {
        RANDOM_GROUP.addInvite(RANDOM_USER);
        RANDOM_GROUP = groupTRepository.save(RANDOM_GROUP);

        RANDOM_GROUP.setInvites(new HashSet<>());
        RANDOM_GROUP = groupTRepository.save(RANDOM_GROUP);

        // Should not throw exception
        userRepository.findById(RANDOM_USER.getId()).orElseThrow(() -> new VibentException(VibentError.GROUP_NOT_FOUND));

        // Should have to invites
        Assert.assertEquals(0, RANDOM_GROUP.getInvites().size());
    }
}
