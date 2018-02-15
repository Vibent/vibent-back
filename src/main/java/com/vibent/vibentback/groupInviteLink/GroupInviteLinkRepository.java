package com.vibent.vibentback.groupInviteLink;

import org.springframework.data.repository.CrudRepository;




public interface GroupInviteLinkRepository extends CrudRepository<GroupInviteLink, Long> {

    GroupInviteLink findById(long id);

    GroupInviteLink findByHash(String hash);

    Iterable<GroupInviteLink> findByGroupRef(String ref);

}
