package com.vibent.vibentback.bubble.alimentation.bringing;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface AlimentationBringingRepository extends CrudRepository<AlimentationBringing, Long> {

    AlimentationBringing findById(long id);
    ArrayList<AlimentationBringing> findByEntryId(long id);
    AlimentationBringing findByUserRef(String ref);

}
