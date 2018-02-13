package com.gitlab.vibent.vibentback.groupInviteLink;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GroupInviteLinkService {

    GroupInviteLinkRepository groupInviteLinkRepository;

    public GroupInviteLink getGroupInviteLink(long id){
        return groupInviteLinkRepository.findById(id);
    }
}
