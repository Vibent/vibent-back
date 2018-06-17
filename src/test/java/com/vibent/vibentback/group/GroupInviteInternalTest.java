package com.vibent.vibentback.group;

import com.vibent.vibentback.ConnectedUserUtils;
import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.api.group.*;
import com.vibent.vibentback.common.util.TokenUtils;
import com.vibent.vibentback.group.membership.Membership;
import com.vibent.vibentback.group.membership.MembershipRequestRepository;
import com.vibent.vibentback.group.membership.MembershipService;
import io.jsonwebtoken.Claims;
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

        Membership RANDOM_MEMBERSHIP = new Membership(RANDOM_USER, RANDOM_GROUP, false);
        when(connectedUserUtils.getConnectedUser()).thenReturn(RANDOM_USER);
        when(membershipRequestRepository.existsByUserAndGroup(RANDOM_USER, RANDOM_GROUP)).thenReturn(false);
        when(membershipService.addMembership(RANDOM_GROUP, RANDOM_USER, false)).thenReturn(RANDOM_MEMBERSHIP);
        when(groupTRepository.findByRef(RANDOM_GROUP.getRef())).thenReturn(Optional.ofNullable(RANDOM_GROUP));
    }

    @Test
    public void generateInviteTokenTest() {
        InviteTokenResponse response = controller.getInviteToken(RANDOM_GROUP.getRef());
        Claims claims = tokenUtils.validateJWTToken(response.getToken());
        Assert.assertEquals(claims.getSubject(), RANDOM_GROUP.getRef());
        Assert.assertEquals(claims.getIssuer(), "Vibent");
        Assert.assertNotNull(claims.getExpiration());
    }

    @Test
    public void validateInviteTokenTest() {
        String token = controller.getInviteToken(RANDOM_GROUP.getRef()).getToken();
        DetailledGroupResponse response = controller.validateInviteToken(new ValidateInviteTokenRequest(token));

        Assert.assertEquals(response.getGroup(), RANDOM_GROUP);
    }
}
