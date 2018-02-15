package com.vibent.vibentback.bubble.alimentation.bring;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface AlimentationBringRepository extends CrudRepository<AlimentationBring, Long> {

    AlimentationBring findById(long id);
    ArrayList<AlimentationBring> findByEntryId(long id);
    AlimentationBring findByUserRef(String ref);

}
