package com.gitlab.vibent.vibentback.groupT;

import org.springframework.data.repository.CrudRepository;
import java.util.ArrayList;


public interface GroupTRepository extends CrudRepository<GroupT, Long> {

    GroupT findById(long id);

    GroupT findByRef(String ref);

    ArrayList<GroupT> findByName(String name);

}
