package com.gitlab.vibent.vibentback.groupMembership;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GroupMembershipService {

    GroupMembershipRepository groupMembershipRepository;

    public GroupMembership getGroupMembership(long id){
        return groupMembershipRepository.findById(id);
    }
}
