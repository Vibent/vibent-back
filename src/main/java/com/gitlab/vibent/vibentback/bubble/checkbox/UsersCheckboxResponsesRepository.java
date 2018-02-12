package com.gitlab.vibent.vibentback.bubble.checkbox;


import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface UsersCheckboxResponsesRepository extends CrudRepository<UsersCheckboxResponses, Long> {

    UsersCheckboxResponses findById(long id);
    ArrayList<UsersCheckboxResponses> findByCheckboxResponseId(long id);

}
