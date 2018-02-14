package com.vibent.vibentback.groupT;

import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;


public interface GroupTRepository extends CrudRepository<GroupT, Long> {

    GroupT findById(long id);

    GroupT findByRef(String ref);

    ArrayList<GroupT> findByName(String name);

    @Transactional
    int deleteByRef(String ref);

}
