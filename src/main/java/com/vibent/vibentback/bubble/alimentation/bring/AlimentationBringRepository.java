package com.vibent.vibentback.bubble.alimentation.bring;

import org.springframework.data.repository.CrudRepository;

public interface AlimentationBringRepository extends CrudRepository<AlimentationBring, Long> {

    AlimentationBring findById(long id);
    Iterable<AlimentationBring> findByEntryId(long id);
    AlimentationBring findByUserRef(String ref);

}
