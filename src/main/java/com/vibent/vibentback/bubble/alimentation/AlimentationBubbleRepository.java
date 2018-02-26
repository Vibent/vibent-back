package com.vibent.vibentback.bubble.alimentation;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;


public interface AlimentationBubbleRepository extends CrudRepository<AlimentationBubble, Long> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE alimentation_bubble SET deleted = FALSE WHERE id = :id", nativeQuery = true)
    int recover(@Param("id") Long id);

    @Query(value = "SELECT deleted FROM alimentation_bubble WHERE id = :id", nativeQuery = true)
    boolean isDeleted(@Param("id") Long id);
}
