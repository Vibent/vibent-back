package com.gitlab.vibent.vibentback.groupT;

import org.springframework.data.repository.CrudRepository;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface GroupTRepository extends CrudRepository<GroupT, Long> {

    GroupT findById(long id);

    GroupT findByRef(String ref);

    ArrayList<GroupT> findByName(String name);

}
