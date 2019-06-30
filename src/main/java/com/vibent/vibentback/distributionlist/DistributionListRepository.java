package com.vibent.vibentback.distributionlist;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface DistributionListRepository extends CrudRepository<DistributionList, Long> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE distribution_list SET deleted = FALSE WHERE id = :id", nativeQuery = true)
    int recover(@Param("id") Long id);

    @Query(value = "SELECT deleted FROM distribution_list WHERE id = :id", nativeQuery = true)
    boolean isDeleted(@Param("id") Long id);

}
