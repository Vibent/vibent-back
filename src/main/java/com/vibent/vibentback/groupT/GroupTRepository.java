package com.vibent.vibentback.groupT;

import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;



public interface GroupTRepository extends CrudRepository<GroupT, Long> {

    GroupT findById(long id);

    GroupT findByRef(String ref);

    Iterable<GroupT> findByName(String name);

    @Transactional
    int deleteByRef(String ref);

}
