package com.vibent.vibentback.distributionlist;

import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.common.error.VibentException;
import com.vibent.vibentback.common.permission.VibentPermissionEvaluator;
import com.vibent.vibentback.event.EventRepository;
import com.vibent.vibentback.event.participation.EventParticipationRepository;
import com.vibent.vibentback.distributionlist.api.DistributionListRequest;
import com.vibent.vibentback.distributionlist.api.DistributionListUpdateRequest;
import com.vibent.vibentback.distributionlist.membership.DistributionListMembership;
import com.vibent.vibentback.user.UserRepository;
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
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class DistributionListInternalTest extends VibentTest {

    @Autowired
    @InjectMocks
    private DistributionListController controller;

    @MockBean
    DistributionListRepository distributionListRepository;

    @MockBean
    UserRepository userRepository;

    @MockBean
    VibentPermissionEvaluator permissionEvaluator;

    @MockBean
    private EventRepository eventRepository;

    @MockBean
    EventParticipationRepository eventParticipationRepository;

    private DistributionListRequest RANDOM_DISTRIBUTION_LIST_REQUEST;
    private DistributionListUpdateRequest RANDOM_DISTRIBUTION_LIST_UPDATE_REQUEST;
    private DistributionListMembership RANDOM_DISTRIBUTION_LIST_MEMBERSHIP;

    @Before
    public void setUp() {
        super.setUp();
        MockitoAnnotations.initMocks(this);

        RANDOM_DISTRIBUTION_LIST_REQUEST = new DistributionListRequest();
        RANDOM_DISTRIBUTION_LIST_REQUEST.setEventRef(RANDOM_EVENT.getRef());
        RANDOM_DISTRIBUTION_LIST_REQUEST.setDescription("Descript");
        RANDOM_DISTRIBUTION_LIST_REQUEST.setTitle("Titlelittle");

        RANDOM_DISTRIBUTION_LIST_UPDATE_REQUEST = new DistributionListUpdateRequest();
        RANDOM_DISTRIBUTION_LIST_UPDATE_REQUEST.setTitle("New title");
        RANDOM_DISTRIBUTION_LIST_UPDATE_REQUEST.setDescription("New Desc");

        RANDOM_DISTRIBUTION_LIST_MEMBERSHIP = new DistributionListMembership();
        RANDOM_DISTRIBUTION_LIST_MEMBERSHIP.setUser(RANDOM_USER);
        RANDOM_DISTRIBUTION_LIST_MEMBERSHIP.setDistributionList(RANDOM_DISTRIBUTION_LIST);

        when(permissionEvaluator.hasPermission(any(), any(), any(), any())).thenReturn(false);
        when(permissionEvaluator.hasPermission(eq(AUTHENTICATION), any(), any(), any())).thenReturn(true);
        when(userRepository.findByRef((String) AUTHENTICATION.getPrincipal())).thenReturn(Optional.of(RANDOM_USER));
        when(distributionListRepository.save(RANDOM_DISTRIBUTION_LIST)).thenReturn(RANDOM_DISTRIBUTION_LIST);
        when(distributionListRepository.findById(RANDOM_DISTRIBUTION_LIST.getId())).thenReturn(Optional.of(RANDOM_DISTRIBUTION_LIST));
        when(eventRepository.findByRef(RANDOM_EVENT.getRef())).thenReturn(Optional.of(RANDOM_EVENT));
    }

    @Test
    public void getConnectedUserDistributionLists() {
        RANDOM_USER.getDistributionListMemberships().add(RANDOM_DISTRIBUTION_LIST_MEMBERSHIP);
        Set<DistributionList> distributionLists = controller.getConnectedDistributionLists();
        Assert.assertNotEquals(0, distributionLists.size());
    }


    @Test
    public void getDistributionList() {
        DistributionList distributionList = controller.getDistributionList(RANDOM_DISTRIBUTION_LIST.getId());
        Assert.assertEquals(RANDOM_DISTRIBUTION_LIST.getTitle(), distributionList.getTitle());
    }

    @Test
    public void addDistributionList() {
        RANDOM_DISTRIBUTION_LIST.setId(null);
        DistributionList distributionList = controller.createDistributionList(RANDOM_DISTRIBUTION_LIST_REQUEST);
        Assert.assertEquals(RANDOM_DISTRIBUTION_LIST.getTitle(), distributionList.getTitle());
    }

    @Test(expected = VibentException.class)
    public void addDistributionListWithInvalidEvent() {
        RANDOM_DISTRIBUTION_LIST_REQUEST.setEventRef("invalid");
        controller.createDistributionList(RANDOM_DISTRIBUTION_LIST_REQUEST);
    }

    @Test
    public void updateDistributionList() {
        DistributionList distributionList = controller.updateDistributionList(RANDOM_DISTRIBUTION_LIST.getId(), RANDOM_DISTRIBUTION_LIST_UPDATE_REQUEST);
        Assert.assertEquals(distributionList.getTitle(), RANDOM_DISTRIBUTION_LIST_UPDATE_REQUEST.getTitle());
    }

    @Test
    public void deleteDistributionList() {
        controller.deleteDistributionList(RANDOM_DISTRIBUTION_LIST.getId());
    }
}
