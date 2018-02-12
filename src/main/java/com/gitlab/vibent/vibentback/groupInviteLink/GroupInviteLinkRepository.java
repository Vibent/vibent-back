package com.gitlab.vibent.vibentback.groupInviteLink;

import com.gitlab.vibent.vibentback.eventParticipation.EventParticipation;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;


public interface GroupInviteLinkRepository extends CrudRepository<GroupInviteLink, Long> {

    GroupInviteLink findById(long id);
    GroupInviteLink findByHash(String hash);
    ArrayList<GroupInviteLink> findByGroupRef(String ref);

}
