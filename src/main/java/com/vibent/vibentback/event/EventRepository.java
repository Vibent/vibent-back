package com.vibent.vibentback.event;

import com.vibent.vibentback.common.util.CrudRefRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface EventRepository extends CrudRefRepository<Event, Long> {

    Optional<Event> findByRef(String ref);

    @Transactional
    int deleteByRef(String ref);

    @Modifying
    @Transactional
    @Query(value = "UPDATE event SET deleted = FALSE WHERE ref = :ref", nativeQuery = true)
    int recover(@Param("ref") String ref);

    @Query(value = "SELECT deleted FROM event WHERE ref = :ref", nativeQuery = true)
    boolean isDeleted(@Param("ref") String ref);

}
