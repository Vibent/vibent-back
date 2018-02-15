package com.vibent.vibentback.bubble.planning.entry;


import org.springframework.data.repository.CrudRepository;


import java.util.Date;

public interface PlanningEntryRepository extends CrudRepository<PlanningEntry, Long> {

    PlanningEntry findById(long id);
    Iterable<PlanningEntry> findByBubbleId(long id);
    Iterable<PlanningEntry> findByCreatorRef(String ref);
    Iterable<PlanningEntry> findByStart(Date start);
    Iterable<PlanningEntry> findByEnd(Date end);

}
