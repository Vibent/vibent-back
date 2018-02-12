package com.gitlab.vibent.vibentback.bubble.planning;


import org.springframework.data.repository.CrudRepository;

public interface PlanningBubbleRepository extends CrudRepository<PlanningBubble, Long> {

    PlanningBubble findById(long id);

}
