package com.vibent.vibentback.groupMembership;

import org.springframework.data.repository.CrudRepository;




public interface GroupMembershipRepository extends CrudRepository<GroupMembership, Long> {

    GroupMembership findById(long id);

    Iterable<GroupMembership> findByUserRef(String ref);

    Iterable<GroupMembership>  findByGroupRef(String ref);
}