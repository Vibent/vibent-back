package com.vibent.vibentback.groupInviteLink;

import org.springframework.data.repository.CrudRepository;

import java.util.Set;


public interface GroupInviteLinkRepository extends CrudRepository<GroupInviteLink, Long> {

    GroupInviteLink findById(long id);

    GroupInviteLink findByHash(String hash);

    Set<GroupInviteLink> findByGroupRef(String ref);

}
