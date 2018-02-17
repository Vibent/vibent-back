package com.vibent.vibentback.bubble.alimentation.bring;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface AlimentationBringRepository extends CrudRepository<AlimentationBring, Long> {

    Iterable<AlimentationBring> findByEntryId(long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE alimentation_bring SET deleted = FALSE WHERE id = :id", nativeQuery = true)
    int recover(@Param("id") Long id);

    @Query(value = "SELECT deleted FROM alimentation_bring WHERE id = :id", nativeQuery = true)
    boolean isDeleted(@Param("id") Long id);

    @Query(value = "SELECT bubble.id FROM alimentation_bubble bubble\n" +
            "JOIN alimentation_entry entry ON bubble.id = entry.bubble_id\n" +
            "JOIN alimentation_bring bring ON entry.id = bring.entry_id\n" +
            "WHERE bring.id = :id", nativeQuery = true)
    Long getBubbleId(@Param("id") Long id);
}
