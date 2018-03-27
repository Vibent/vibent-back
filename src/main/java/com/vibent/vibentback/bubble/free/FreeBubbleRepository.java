package com.vibent.vibentback.bubble.free;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface FreeBubbleRepository extends CrudRepository<FreeBubble, Long> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE free_bubble SET deleted = FALSE WHERE id = :id", nativeQuery = true)
    int recover(@Param("id") Long id);

    @Query(value = "SELECT deleted FROM free_bubble WHERE id = :id", nativeQuery = true)
    boolean isDeleted(@Param("id") Long id);

}
