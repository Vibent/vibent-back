package com.vibent.vibentback.group.membership;

import com.vibent.vibentback.group.GroupT;
import com.vibent.vibentback.user.User;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface MembershipRequestRepository extends CrudRepository<MembershipRequest, Long> {
    @Transactional
    int deleteByUserAndGroup(User user, GroupT group);

    Optional<MembershipRequest> findByUserAndGroup(User user, GroupT groupT);

    boolean existsByUserAndGroup(User user, GroupT groupT);
}
