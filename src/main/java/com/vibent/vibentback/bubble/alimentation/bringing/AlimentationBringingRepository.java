package com.vibent.vibentback.bubble.alimentation.bringing;

import org.springframework.data.repository.CrudRepository;

public interface AlimentationBringingRepository extends CrudRepository<AlimentationBringing, Long> {

    AlimentationBringing findById(long id);
    Iterable<AlimentationBringing> findByEntryId(long id);
    AlimentationBringing findByUserRef(String ref);

}
