package com.vibent.vibentback.groupT;

import com.vibent.vibentback.api.groupT.DetailledGroupResponse;
import com.vibent.vibentback.api.groupT.GroupRequest;
import com.vibent.vibentback.api.groupT.GroupUpdateRequest;
import com.vibent.vibentback.api.groupT.PublicGroupResponse;
import com.vibent.vibentback.api.user.DetailledUserResponse;
import com.vibent.vibentback.event.Event;
import com.vibent.vibentback.groupT.membership.Membership;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/group")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GroupTController {

    private final GroupTService groupTService;

    @RequestMapping(method = RequestMethod.GET, value = "/me")
    Set<DetailledGroupResponse> getConnectedUserGroups() {
        log.info("Getting the connected user's groups");
        Set<DetailledGroupResponse> response = new HashSet<>();
        groupTService.getConnectedUserMemberships().forEach(m -> response.add(new DetailledGroupResponse(m.getGroup())));
        return response;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/public/{groupRef}")
    PublicGroupResponse getGroupTPublicInfo(@PathVariable String groupRef) {
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
    String getInviteToken(@PathVariable String groupRef) {
        log.info("Getting invite token for group : {}", groupRef);
        return groupTService.generateInviteToken(groupRef);
    }
}
