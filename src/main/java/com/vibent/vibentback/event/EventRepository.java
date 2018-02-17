package com.vibent.vibentback.event;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

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

    @Modifying
    @Transactional
    @Query(value = "UPDATE user SET event = FALSE WHERE ref = :ref", nativeQuery = true)
    int recover(@Param("ref") String ref);

    @Query(value = "SELECT deleted FROM event WHERE ref = :ref", nativeQuery = true)
    boolean isDeleted(@Param("ref") String ref);


}
