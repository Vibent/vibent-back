package com.vibent.vibentback.bubble.alimentation.entry;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface AlimentationEntryRepository extends CrudRepository<AlimentationEntry, Long> {

    AlimentationEntry findById(long id);
    ArrayList<AlimentationEntry> findByBubbleId(long id);
    ArrayList<AlimentationEntry> findByName(String name);
    ArrayList<AlimentationEntry> findByType(String type);

}