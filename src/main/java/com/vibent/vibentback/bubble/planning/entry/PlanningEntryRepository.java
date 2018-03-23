package com.vibent.vibentback.bubble.planning.entry;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface PlanningEntryRepository extends CrudRepository<PlanningEntry, Long> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE planning_entry SET deleted = FALSE WHERE id = :id", nativeQuery = true)
    int recover(@Param("id") long id);

    @Query(value = "SELECT deleted FROM planning_entry WHERE id = :id", nativeQuery = true)
    boolean isDeleted(@Param("id") long id);
}
