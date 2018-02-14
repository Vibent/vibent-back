package com.vibent.vibentback.groupMembership;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;


public interface GroupMembershipRepository extends CrudRepository<GroupMembership, Long> {

    GroupMembership findById(long id);

    ArrayList<GroupMembership> findByUserRef(String ref);

    ArrayList<GroupMembership>  findByGroupRef(String ref);
}