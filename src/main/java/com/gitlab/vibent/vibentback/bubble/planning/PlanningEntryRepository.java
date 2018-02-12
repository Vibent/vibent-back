package com.gitlab.vibent.vibentback.bubble.planning;


import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.Date;

public interface PlanningEntryRepository extends CrudRepository<PlanningEntry, Long> {

    PlanningEntry findById(long id);
    ArrayList<PlanningEntry> findByBubbleId(long id);
    ArrayList<PlanningEntry> findByCreatorRef(String ref);
    ArrayList<PlanningEntry> findByStart(Date start);
    ArrayList<PlanningEntry> findByEnd(Date end);

}
