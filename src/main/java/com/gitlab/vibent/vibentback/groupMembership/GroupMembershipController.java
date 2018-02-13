package com.gitlab.vibent.vibentback.groupMembership;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/groupmembership")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GroupMembershipController {

    private final GroupMembershipService groupMembershipService;

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    GroupMembership getGroupMembership(@PathVariable long id) {
        log.info("Get group membership with id : {}", id);
        return groupMembershipService.getGroupMembership(id);
    }
}
