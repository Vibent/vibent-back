package com.vibent.vibentback.event;

import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

import java.util.Date;

public interface EventRepository extends CrudRepository<Event, Long> {

    Event findById(long id);

    Event findByRef(String ref);

    Iterable<Event> findByGroupRef(String groupRef);

    Iterable<Event> findByTitle(String title);

    Iterable<Event> findByStartDate(Date date);

    Iterable<Event> findByEndDate(Date date);

    @Transactional
    int deleteByRef(String ref);


}
