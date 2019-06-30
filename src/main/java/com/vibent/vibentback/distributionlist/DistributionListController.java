package com.vibent.vibentback.distributionlist;

import com.vibent.vibentback.common.api.InviteTokenResponse;
import com.vibent.vibentback.common.api.MailInviteIdRequest;
import com.vibent.vibentback.distributionlist.api.DistributionListRequest;
import com.vibent.vibentback.distributionlist.api.DistributionListUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/distribution-list")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DistributionListController {

    private final DistributionListService distributionListService;

    @RequestMapping(method = RequestMethod.GET, value = "/me")
    Set<DistributionList> getConnectedDistributionLists() {
        log.info("Getting the connected user's distribution lists");
        return distributionListService.getConnectedDistributionLists();
    }

    @PreAuthorize("hasPermission(#id, 'DistributionList', 'read')")
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    DistributionList getDistributionList(@PathVariable long id) {
        log.info("Get distribution list with id : {}", id);
        return distributionListService.getDistributionList(id);
    }

    @PreAuthorize("hasPermission(#request.eventRef, 'Event', 'read')")
    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    DistributionList createDistributionList(@Valid @RequestBody DistributionListRequest request) {
        log.info("Creating distribution list with body : {}", request.toString());
        return distributionListService.createDistributionList(request);
    }

    @PreAuthorize(value = "hasPermission(#id, 'DistributionList', 'read')")
    @RequestMapping(method = RequestMethod.POST, value = "/{id}/leave")
    void leaveDistributionList(@PathVariable Long id) {
        log.info("Leaving distribution list {}", id);
        distributionListService.leaveDistributionList(id);
    }

    @PreAuthorize(value = "hasPermission(#id, 'DistributionList', 'write')")
    @RequestMapping(method = RequestMethod.PATCH, value = "/{id}")
    DistributionList updateDistributionList(@PathVariable Long id, @Valid @RequestBody DistributionListUpdateRequest request) {
        log.info("Updating distribution list with body : {}", request.toString());
        return distributionListService.updateDistributionList(id, request);
    }

    @PreAuthorize(value = "hasPermission(#id, 'DistributionList', 'write')")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    void deleteDistributionList(@PathVariable long id) {
        log.info("Deleting distribution list with id : {}", id);
        distributionListService.deleteDistributionList(id);
    }

    @PreAuthorize(value = "hasPermission(#id, 'DistributionList', 'write')")
    @RequestMapping(method = RequestMethod.GET, value = "/{id}/inviteToken")
    InviteTokenResponse getDistributionListInviteToken(@PathVariable Long id) {
        log.info("Getting invite token for distribution list : {}", id);
        return new InviteTokenResponse(distributionListService.generateDistributionListInviteToken(id));
    }

    @PreAuthorize(value = "hasPermission(#id, 'DistributionList', 'write')")
    @RequestMapping(method = RequestMethod.POST, value = "/mailInvite")
    void mailInvite(@Valid @RequestBody MailInviteIdRequest request) {
        log.info("Sending mail invite for distribution list : {}", request.getId());
        distributionListService.sendMailInvite(request);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/validateInviteToken/{token:.+}")
    DistributionList validateInviteToken(@PathVariable String token) {
        log.info("Validating distribution list invite token : {}", token);
        return distributionListService.validateInviteToken(token);
    }
}
