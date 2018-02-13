package com.gitlab.vibent.vibentback.groupInviteLink;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/groupinvitelink")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GroupInviteLinkController {

    private final GroupInviteLinkService groupInviteLinkService;

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    GroupInviteLink getGroupInviteLink(@PathVariable long id) {
        log.info("Get group invite link with id : {}", id);
        return groupInviteLinkService.getGroupInviteLink(id);
    }
}
