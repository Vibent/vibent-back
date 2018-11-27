package com.vibent.vibentback.group.groupLinkToUserTest;

import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.auth.VibentAuthentication;
import com.vibent.vibentback.common.error.VibentError;
import com.vibent.vibentback.common.error.VibentException;
import com.vibent.vibentback.group.GroupT;
import com.vibent.vibentback.group.GroupTRepository;
import com.vibent.vibentback.group.membership.Membership;
import com.vibent.vibentback.group.membership.MembershipService;
import com.vibent.vibentback.user.User;
import com.vibent.vibentback.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.stream.Collectors;


@Slf4j
@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class GroupMembershipDataTest extends VibentTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    GroupTRepository groupTRepository;
    @Autowired
    MembershipService membershipService;

    @Before
    public void setUp() {
        super.setUp();
        RANDOM_USER = userRepository.save(RANDOM_USER);
        RANDOM_GROUP = groupTRepository.save(RANDOM_GROUP);

        SecurityContextHolder.getContext().setAuthentication(AUTHENTICATION);
    }

    @Test
    public void testAddGroupMembership() {

        membershipService.addMembership(RANDOM_GROUP, RANDOM_USER, false);

        GroupT group = groupTRepository.findById(RANDOM_GROUP.getId()).orElseThrow(() -> new VibentException(VibentError.GROUP_NOT_FOUND));
        Assert.assertNotEquals(0, group.getMemberships().size());

        User user = userRepository.findById(RANDOM_USER.getId()).orElseThrow(() -> new VibentException(VibentError.USER_NOT_FOUND));
        Assert.assertNotEquals(0, user.getMemberships().size());

        Assert.assertTrue(group.getMemberships().stream()
                .map(Membership::getUser).collect(Collectors.toSet()).contains(user));
        Assert.assertFalse(group.getMemberships().stream()
                .filter(Membership::getAdmin)
                .map(Membership::getUser).collect(Collectors.toSet()).contains(user));
    }

    @Test
    public void testAddGroupAdminship() {

        membershipService.addMembership(RANDOM_GROUP, RANDOM_USER, true);

        GroupT group = groupTRepository.findById(RANDOM_GROUP.getId()).orElseThrow(() -> new VibentException(VibentError.GROUP_NOT_FOUND));
        Assert.assertNotEquals(0, group.getMemberships().size());

        User user = userRepository.findById(RANDOM_USER.getId()).orElseThrow(() -> new VibentException(VibentError.USER_NOT_FOUND));
        Assert.assertNotEquals(0, user.getMemberships().size());

        Assert.assertTrue(group.getMemberships().stream()
                .filter(Membership::getAdmin)
                .map(Membership::getUser).collect(Collectors.toSet()).contains(user));
    }

    @Test
    public void testRemoveGroupMembership() {

        membershipService.addMembership(RANDOM_GROUP, RANDOM_USER, false);
        membershipService.removeMembership(RANDOM_GROUP, RANDOM_USER);


        GroupT group = groupTRepository.findById(RANDOM_GROUP.getId()).orElseThrow(() -> new VibentException(VibentError.GROUP_NOT_FOUND));
        Assert.assertEquals(0, group.getMemberships().size());

        User user = userRepository.findById(RANDOM_USER.getId()).orElseThrow(() -> new VibentException(VibentError.USER_NOT_FOUND));
        Assert.assertEquals(0, user.getMemberships().size());

        Assert.assertFalse(group.getMemberships().stream()
                .filter(Membership::getAdmin)
                .map(Membership::getUser).collect(Collectors.toSet()).contains(user));
    }

    @Test
    public void testChangeGroupMembership() {

        membershipService.addMembership(RANDOM_GROUP, RANDOM_USER, false);

        membershipService.changeAdminship(RANDOM_GROUP, RANDOM_USER, true);


        Membership membership = RANDOM_USER.getMemberships().stream().filter(m -> m.getGroup().equals(RANDOM_GROUP)).findFirst()
                .orElseThrow(() -> new VibentException(VibentError.MEMBERSHIP_NOT_FOUND));
        Assert.assertTrue(membership.getAdmin());
    }

    @Test
    public void testDeleteSingleMembership() {

        membershipService.addMembership(RANDOM_GROUP, RANDOM_USER, false);

        membershipService.removeMembership(RANDOM_GROUP, RANDOM_USER);


        // Assert membership was deleted on both ends
        Assert.assertFalse(RANDOM_USER.getMemberships().stream().anyMatch(m -> m.getGroup().equals(RANDOM_GROUP)));
        Assert.assertFalse(RANDOM_GROUP.getMemberships().stream().anyMatch(m -> m.getUser().equals(RANDOM_USER)));

        // Assert member and group are not deleted
        Assert.assertTrue(userRepository.findByRef(RANDOM_USER.getRef()).isPresent());
        Assert.assertTrue(groupTRepository.findByRef(RANDOM_GROUP.getRef()).isPresent());
    }

    @Test
    public void testCreateGroupMembershipRequest() {

        membershipService.addMembershipRequestForConnectedUser(RANDOM_GROUP.getRef());

        Assert.assertTrue(RANDOM_USER.getRequests().stream().anyMatch(m -> m.getGroup().equals(RANDOM_GROUP)));
        Assert.assertTrue(RANDOM_GROUP.getRequests().stream().anyMatch(m -> m.getUser().equals(RANDOM_USER)));
    }

    @Test
    public void testDeleteGroupMembershipRequest() {

        membershipService.addMembershipRequestForConnectedUser(RANDOM_GROUP.getRef());

        membershipService.deleteMembershipRequest(RANDOM_GROUP.getRef(), RANDOM_USER.getRef());

        // Assert membership request was deleted on both ends
        Assert.assertFalse(RANDOM_USER.getRequests().stream().anyMatch(m -> m.getGroup().equals(RANDOM_GROUP)));
        Assert.assertFalse(RANDOM_GROUP.getRequests().stream().anyMatch(m -> m.getUser().equals(RANDOM_USER)));

        // Assert member and group are not deleted
        Assert.assertTrue(userRepository.findByRef(RANDOM_USER.getRef()).isPresent());
        Assert.assertTrue(groupTRepository.findByRef(RANDOM_GROUP.getRef()).isPresent());
    }
}