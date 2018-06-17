package com.vibent.vibentback.group.membership;

import com.vibent.vibentback.group.GroupT;
import com.vibent.vibentback.user.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

public interface MembershipRepository extends CrudRepository<Membership, Long> {

    Optional<Membership> findByUserAndGroup(User user, GroupT groupT);
    Set<Membership> findByUser(User user);
    Set<Membership> findByGroup(GroupT groupT);

    @Transactional
    int deleteByGroup(GroupT groupT);

    @Modifying
    @Transactional
    @Query(value = "UPDATE membership SET deleted = FALSE WHERE group_id = :groupId", nativeQuery = true)
    int recoverByGroup(@Param("groupId") Long groupId);

    @Transactional
    int deleteByUser(User user);

    @Modifying
    @Transactional
    @Query(value = "UPDATE membership SET deleted = FALSE WHERE user_id = :userId", nativeQuery = true)
    int recoverByUser(@Param("userId") Long userId);

    @Transactional
    int deleteByUserAndGroup(User user, GroupT group);

    @Modifying
    @Transactional
    @Query(value = "UPDATE membership SET deleted = FALSE WHERE user_id = :userId AND group_id = :groupId", nativeQuery = true)
    int recoverByUserAndGroup(@Param("userId") Long userId, @Param("groupId") Long groupId);

}
