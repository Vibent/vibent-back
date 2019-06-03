package com.vibent.vibentback.group;

import com.vibent.vibentback.event.Event;
import com.vibent.vibentback.event.api.DetailledEventResponse;
import com.vibent.vibentback.group.api.*;
import com.vibent.vibentback.group.membership.Membership;
import com.vibent.vibentback.group.membership.MembershipRequest;
import com.vibent.vibentback.group.membership.MembershipService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/group")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
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

    @PreAuthorize(value = "hasPermission(#groupRef, 'GroupT', 'read')")
    @RequestMapping(method = RequestMethod.GET, value = "/{groupRef}")
    DetailledGroupResponse getGroupT(@PathVariable String groupRef) {
        log.info("Get group with ref : {}", groupRef);
        return new DetailledGroupResponse(groupTService.getGroupT(groupRef));
    }

    @PreAuthorize(value = "hasPermission(#groupRef, 'GroupT', 'read')")
    @RequestMapping(method = RequestMethod.GET, value = "/{groupRef}/event")
    Set<DetailledEventResponse> getGroupEvents(@PathVariable String groupRef) {
        log.info("Get group events with ref : {}", groupRef);
        Set<Event> events = groupTService.getGroupEvents(groupRef);
        return events.stream().map(DetailledEventResponse::new).collect(Collectors.toSet());
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    DetailledGroupResponse createGroupT(@Valid @RequestBody GroupRequest request) {
        log.info("Creating group with body : {}", request.toString());
        return new DetailledGroupResponse(groupTService.createGroupT(request));
    }

    @PreAuthorize(value = "hasPermission(#groupRef, 'GroupT', 'write')")
    @RequestMapping(method = RequestMethod.PATCH, value = "/{groupRef}")
    DetailledGroupResponse updateGroupT(@PathVariable String groupRef, @Valid @RequestBody GroupUpdateRequest request) {
        log.info("Update group with ref {} body : {}", groupRef, request.toString());
        return new DetailledGroupResponse(groupTService.updateGroupT(groupRef, request));
    }

    @PreAuthorize(value = "hasPermission(#groupRef, 'GroupT', 'write')")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "/{groupRef}")
    void deleteGroupT(@PathVariable String groupRef) {
        log.info("Deleting group with ref : {}", groupRef);
        groupTService.deleteGroupT(groupRef);
    }

    @PreAuthorize(value = "hasPermission(#groupRef, 'GroupT', 'write')")
    @RequestMapping(method = RequestMethod.GET, value = "/{groupRef}/inviteToken")
    InviteTokenResponse getInviteToken(@PathVariable String groupRef) {
        log.info("Getting invite token for group : {}", groupRef);
        return new InviteTokenResponse(groupTService.generateInviteToken(groupRef));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/validateInviteToken/{token:.+}")
    DetailledGroupResponse validateInviteToken(@PathVariable String token) {
        log.info("Validating invite token : {}", token);
        return new DetailledGroupResponse(groupTService.validateInviteToken(token));
    }

    @PreAuthorize(value = "hasPermission(#request.ref, 'GroupT', 'write')")
    @RequestMapping(method = RequestMethod.POST, value = "/mailInvite")
    void mailInvite(@Valid @RequestBody MailInviteRequest request) {
        log.info("Sending mail invite for group : {}", request.getRef());
        groupTService.sendMailInvite(request);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{groupRef}/request")
    UserMembershipRequestResponse requestMembership(@PathVariable String groupRef) {
        log.info("Connected user requesting membership to {}", groupRef);
        MembershipRequest membershipRequest = membershipService.addMembershipRequestForConnectedUser(groupRef);
        return new UserMembershipRequestResponse(membershipRequest.getGroup().getRef());
    }

    @PreAuthorize(value = "hasPermission(#request.groupRef, 'GroupT', 'write')")
    @RequestMapping(method = RequestMethod.POST, value = "/request/accept")
    MembershipResponse acceptMembershipRequest(@Valid @RequestBody AcceptGroupMembershipRequestRequest request) {
        log.info("Accepting group request by {} to {}", request.getUserRef(), request.getGroupRef());
        Membership membership = membershipService.addMembership(request.getGroupRef(), request.getUserRef(), request.isAdmin());
        return new MembershipResponse(membership.getUser().getRef(), membership.getGroup().getRef(), membership.getAdmin());
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{groupRef}/leave")
    void leaveGroup(@PathVariable String groupRef) {
        log.info("Leaving group with ref : {}", groupRef);
        groupTService.leaveGroup(groupRef);
    }
}
