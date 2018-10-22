package com.vibent.vibentback.bubble.planning;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface PlanningBubbleRepository extends CrudRepository<PlanningBubble, Long> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE planning_bubble SET deleted = FALSE WHERE id = :id", nativeQuery = true)
    int recover(@Param("id") Long id);

    @Query(value = "SELECT deleted FROM planning_bubble WHERE id = :id", nativeQuery = true)
    boolean isDeleted(@Param("id") Long id);
}
