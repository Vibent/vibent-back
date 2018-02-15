package com.vibent.vibentback.bubble.alimentation.entry;

import org.springframework.data.repository.CrudRepository;


public interface AlimentationEntryRepository extends CrudRepository<AlimentationEntry, Long> {

    AlimentationEntry findById(long id);
    Iterable<AlimentationEntry> findByBubbleId(long id);
    Iterable<AlimentationEntry> findByName(String name);
    Iterable<AlimentationEntry> findByType(String type);

}