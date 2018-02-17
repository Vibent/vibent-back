package com.vibent.vibentback.bubble.alimentation.entry;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;


public interface AlimentationEntryRepository extends CrudRepository<AlimentationEntry, Long> {

    AlimentationEntry findById(long id);
    Iterable<AlimentationEntry> findByBubbleId(long id);
    Iterable<AlimentationEntry> findByName(String name);
    Iterable<AlimentationEntry> findByType(String type);

    @Transactional
    int deleteById(long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE alimentation_entry SET deleted = FALSE WHERE id = :id", nativeQuery = true)
    int recover(@Param("id") long id);

    @Query(value = "SELECT deleted FROM alimentation_entry WHERE id = :id", nativeQuery = true)
    boolean isDeleted(@Param("id") long id);
}