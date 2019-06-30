package com.vibent.vibentback.distributionlist.membership;

import com.vibent.vibentback.distributionlist.DistributionList;
import com.vibent.vibentback.user.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;


public interface DistributionListMembershipRepository extends CrudRepository<DistributionListMembership, Long> {

    Set<DistributionListMembership> findByUser(User user);

    Set<DistributionListMembership> findByDistributionList(DistributionList distributionList);

    Optional<DistributionListMembership> findByUserAndDistributionList(User user, DistributionList distributionList);

    @Modifying
    @Transactional
    @Query(value = "UPDATE distribution_list_membership SET deleted = FALSE WHERE id = :id", nativeQuery = true)
    int recover(@Param("id") Long id);

    @Query(value = "SELECT deleted FROM distribution_list_membership WHERE id = :id", nativeQuery = true)
    boolean isDeleted(@Param("id") Long id);
}
