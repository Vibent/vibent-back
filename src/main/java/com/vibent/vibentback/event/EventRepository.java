package com.vibent.vibentback.event;

import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;

public interface EventRepository extends CrudRepository<Event, Long> {

    Event findById(long id);

    Event findByRef(String ref);

    ArrayList<Event> findByGroupRef(String groupRef);

    ArrayList<Event> findByTitle(String title);

    ArrayList<Event> findByStartDate(Date date);

    ArrayList<Event> findByEndDate(Date date);

    @Transactional
    int deleteByRef(String ref);


}