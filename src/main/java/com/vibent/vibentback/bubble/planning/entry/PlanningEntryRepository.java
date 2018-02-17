package com.vibent.vibentback.bubble.planning.entry;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


import javax.transaction.Transactional;
import java.util.Date;

public interface PlanningEntryRepository extends CrudRepository<PlanningEntry, Long> {

    PlanningEntry findById(long id);
    Iterable<PlanningEntry> findByBubbleId(long id);
    Iterable<PlanningEntry> findByCreatorRef(String ref);
    Iterable<PlanningEntry> findByStart(Date start);
    Iterable<PlanningEntry> findByEnd(Date end);

    @Transactional
    int deleteById(long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE planning_entry SET deleted = FALSE WHERE id = :id", nativeQuery = true)
    int recover(@Param("id") long id);

    @Query(value = "SELECT deleted FROM planning_entry WHERE id = :id", nativeQuery = true)
    boolean isDeleted(@Param("id") long id);
}
