package com.vibent.vibentback.group;

import com.vibent.vibentback.common.permission.VibentPermissionEvaluator;
import com.vibent.vibentback.group.api.DetailledGroupResponse;
import com.vibent.vibentback.group.api.InviteTokenResponse;
import com.vibent.vibentback.user.ConnectedUserUtils;
import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.common.util.TokenInfo;
import com.vibent.vibentback.common.util.TokenUtils;
import com.vibent.vibentback.group.membership.Membership;
import com.vibent.vibentback.group.membership.MembershipRequestRepository;
import com.vibent.vibentback.group.membership.MembershipService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class GroupInviteInternalTest extends VibentTest {

    @Autowired
    TokenUtils tokenUtils;

    @Autowired
    @InjectMocks
    private GroupTController controller;

    @MockBean
    VibentPermissionEvaluator permissionEvaluator;
    @MockBean
    private GroupTRepository groupTRepository;
    @MockBean
    private MembershipService membershipService;
    @MockBean
    private MembershipRequestRepository membershipRequestRepository;
    @MockBean
    private ConnectedUserUtils connectedUserUtils;

    @Before
    public void setUp() {
        super.setUp();
        MockitoAnnotations.initMocks(this);

        // Needed as invite tokens are based on group ID
        RANDOM_GROUP.setId(5745L);

        Membership RANDOM_MEMBERSHIP = new Membership();
        RANDOM_MEMBERSHIP.setUser(RANDOM_USER);
        RANDOM_MEMBERSHIP.setGroup(RANDOM_GROUP);
        RANDOM_MEMBERSHIP.setAdmin(false);

        when(permissionEvaluator.hasPermission(any(), any(), any(), any())).thenReturn(false);
        when(permissionEvaluator.hasPermission(eq(AUTHENTICATION), any(), any(), any())).thenReturn(true);
        when(connectedUserUtils.getConnectedUser()).thenReturn(RANDOM_USER);
        when(membershipRequestRepository.existsByUserAndGroup(RANDOM_USER, RANDOM_GROUP)).thenReturn(false);
        when(membershipService.addMembership(RANDOM_GROUP, RANDOM_USER, false)).thenReturn(RANDOM_MEMBERSHIP);
        when(groupTRepository.findByRef(RANDOM_GROUP.getRef())).thenReturn(Optional.ofNullable(RANDOM_GROUP));
        when(groupTRepository.findById(RANDOM_GROUP.getId())).thenReturn(Optional.ofNullable(RANDOM_GROUP));
    }

    @Test
    public void generateInviteTokenTest() {
        InviteTokenResponse response = controller.getInviteToken(RANDOM_GROUP.getRef());
        TokenInfo info = tokenUtils.readGroupInviteToken(response.getToken());
        Assert.assertEquals(info.getId(), RANDOM_GROUP.getId());
    }

    @Test
    public void validateInviteTokenTest() {
        String token = controller.getInviteToken(RANDOM_GROUP.getRef()).getToken();
        DetailledGroupResponse response = controller.validateInviteToken(token);

        Assert.assertEquals(response.getGroup(), RANDOM_GROUP);
    }
}
