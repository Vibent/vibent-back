package com.vibent.vibentback.group;

import com.vibent.vibentback.api.group.*;
import com.vibent.vibentback.api.group.membership.AcceptGroupMembershipRequestRequest;
import com.vibent.vibentback.api.group.membership.MembershipResponse;
import com.vibent.vibentback.api.group.membership.UserMembershipRequestResponse;
import com.vibent.vibentback.common.error.VibentError;
import com.vibent.vibentback.common.error.VibentException;
import com.vibent.vibentback.event.Event;
import com.vibent.vibentback.group.membership.Membership;
import com.vibent.vibentback.group.membership.MembershipRequest;
import com.vibent.vibentback.group.membership.MembershipService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/group")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GroupTController {

    private final GroupTService groupTService;
    private final MembershipService membershipService;

    @RequestMapping(method = RequestMethod.GET, value = "/me")
    Set<DetailledGroupResponse> getConnectedUserGroups() {
        log.info("Getting the connected user's groups");
        Set<DetailledGroupResponse> response = new HashSet<>();
        groupTService.getConnectedUserMemberships().forEach(m -> response.add(new DetailledGroupResponse(m.getGroup())));
        return response;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/public/{groupRef}")
    PublicGroupResponse getGroupPublicInfo(@PathVariable String groupRef) {
        log.info("Get public group info with ref : {}", groupRef);
        return new PublicGroupResponse(groupTService.getPublicGroupInfo(groupRef));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{groupRef}")
    DetailledGroupResponse getGroupT(@PathVariable String groupRef) {
        log.info("Get group with ref : {}", groupRef);
        return new DetailledGroupResponse(groupTService.getGroupT(groupRef));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{groupRef}/event")
    Set<Event> getGroupEvents(@PathVariable String groupRef) {
        log.info("Get group events with ref : {}", groupRef);
        return groupTService.getGroupEvents(groupRef);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    DetailledGroupResponse createGroupT(@Valid @RequestBody GroupRequest request) {
        log.info("Creating group with body : {}", request.toString());
        return new DetailledGroupResponse(groupTService.createGroupT(request));
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/{groupRef}")
    DetailledGroupResponse updateGroupT(@PathVariable String groupRef, @Valid @RequestBody GroupUpdateRequest request) {
        log.info("Update group with ref {} body : {}", groupRef, request.toString());
        return new DetailledGroupResponse(groupTService.updateGroupT(groupRef, request));
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "/{groupRef}")
    void deleteGroupT(@PathVariable String groupRef) {
        log.info("Deleting group with ref : {}", groupRef);
        groupTService.deleteGroupT(groupRef);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{groupRef}/inviteToken")
    InviteTokenResponse getInviteToken(@PathVariable String groupRef) {
        log.info("Getting invite token for group : {}", groupRef);
        return new InviteTokenResponse(groupTService.generateInviteToken(groupRef));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/validateToken")
    DetailledGroupResponse validateInviteToken(@Valid @RequestBody ValidateInviteTokenRequest request) {
        log.info("Validating invite token : {}", request.getToken());
        return new DetailledGroupResponse(groupTService.validateInviteToken(request));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{groupRef}/request")
    UserMembershipRequestResponse requestMembership(@PathVariable String groupRef) {
        log.info("Connected user requesting membership to {}", groupRef);
        MembershipRequest membershipRequest = membershipService.addMembershipRequestForConnectedUser(groupRef);
        return new UserMembershipRequestResponse(membershipRequest.getGroup().getRef());
    }

    @RequestMapping(method = RequestMethod.POST, value = "/request/accept")
    MembershipResponse acceptMembershipRequest(@Valid @RequestBody AcceptGroupMembershipRequestRequest request) {
        log.info("Accepting group request by {} to {}", request.getUserRef(), request.getGroupRef());
        Membership membership = membershipService.addMembership(request.getGroupRef(), request.getUserRef(), request.isAdmin());
        return new MembershipResponse(membership.getUser().getRef(), membership.getGroup().getRef(), membership.getAdmin());
    }
}
