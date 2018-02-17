package com.vibent.vibentback.bubble.alimentation.bring;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface AlimentationBringRepository extends CrudRepository<AlimentationBring, Long> {

    AlimentationBring findById(long id);
    Iterable<AlimentationBring> findByEntryId(long id);
    AlimentationBring findByUserRef(String ref);

    @Transactional
    int deleteById(long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE alimentation_bring SET deleted = FALSE WHERE id = :id", nativeQuery = true)
    int recover(@Param("id") long id);

    @Query(value = "SELECT deleted FROM alimentation_bring WHERE id = :id", nativeQuery = true)
    boolean isDeleted(@Param("id") long id);

}
