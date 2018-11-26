package com.vibent.vibentback.group;

import com.vibent.vibentback.common.permission.VibentPermissionEvaluator;
import com.vibent.vibentback.user.ConnectedUserUtils;
import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.api.group.DetailledGroupResponse;
import com.vibent.vibentback.api.group.GroupRequest;
import com.vibent.vibentback.api.group.GroupUpdateRequest;
import com.vibent.vibentback.group.membership.Membership;
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
public class GroupTInternalTest extends VibentTest {

    @Autowired
    @InjectMocks
    private GroupTController controller;

    @MockBean
    private GroupTRepository groupTRepository;
    @MockBean
    private MembershipService membershipService;
    @MockBean
    private ConnectedUserUtils connectedUserUtils;
    @MockBean
    VibentPermissionEvaluator permissionEvaluator;

    private GroupRequest RANDOM_GROUP_REQUEST;
    private GroupUpdateRequest RANDOM_GROUP_UPDATE_REQUEST;

    @Before
    public void setUp() {
        super.setUp();
        MockitoAnnotations.initMocks(this);

        RANDOM_GROUP_REQUEST = new GroupRequest();
        RANDOM_GROUP_REQUEST.setName("Name for group");
        RANDOM_GROUP_REQUEST.setAllAdmins(true);

        RANDOM_GROUP_UPDATE_REQUEST = new GroupUpdateRequest();
        RANDOM_GROUP_UPDATE_REQUEST.setName("New name");

        Membership RANDOM_MEMBERSHIP = new Membership(RANDOM_USER, RANDOM_GROUP, true);

        when(permissionEvaluator.hasPermission(any(), any(), any(), any())).thenReturn(false);
        when(permissionEvaluator.hasPermission(eq(AUTHENTICATION), any(), any(), any())).thenReturn(true);
        when(membershipService.addMembership(RANDOM_GROUP, RANDOM_USER, true)).thenReturn(RANDOM_MEMBERSHIP);
        when(connectedUserUtils.getConnectedUser()).thenReturn(RANDOM_USER);
        when(groupTRepository.findByRef(RANDOM_GROUP.getRef())).thenReturn(Optional.ofNullable(RANDOM_GROUP));
        when(groupTRepository.save(RANDOM_GROUP)).thenReturn(RANDOM_GROUP);
        when(groupTRepository.deleteByRef(RANDOM_GROUP.getRef())).thenReturn(1);
    }

    @Test
    public void getGroupT() {
        DetailledGroupResponse groupT = controller.getGroupT(RANDOM_GROUP.getRef());
        Assert.assertEquals(RANDOM_GROUP.getRef(), groupT.getRef());
    }

    @Test
    public void addGroupT() {
        DetailledGroupResponse groupT = controller.createGroupT(RANDOM_GROUP_REQUEST);
        Assert.assertEquals(groupT.getName(), RANDOM_GROUP_REQUEST.getName());
    }

    @Test
    public void updateGroupT() {
        DetailledGroupResponse groupT = controller.updateGroupT(RANDOM_GROUP.getRef(), RANDOM_GROUP_UPDATE_REQUEST);
        Assert.assertEquals(groupT.getName(), RANDOM_GROUP_UPDATE_REQUEST.getName());
        Assert.assertEquals(groupT.getMemberships().size(), RANDOM_GROUP.getMemberships().size());
    }

    @Test
    public void updateGroupWithEmptyDescriptionShouldSetToNull() {
        GroupUpdateRequest request = new GroupUpdateRequest();
        request.setDescription("");
        DetailledGroupResponse groupT = controller.updateGroupT(RANDOM_GROUP.getRef(), request);
        Assert.assertNull(groupT.getDescription());
    }

    @Test
    public void deleteGroupT() {
        controller.deleteGroupT(RANDOM_GROUP.getRef());
    }
}
