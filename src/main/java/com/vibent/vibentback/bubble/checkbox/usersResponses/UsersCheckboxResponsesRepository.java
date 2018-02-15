package com.vibent.vibentback.bubble.checkbox.usersResponses;


import org.springframework.data.repository.CrudRepository;

public interface UsersCheckboxResponsesRepository extends CrudRepository<UsersCheckboxResponses, Long> {

    UsersCheckboxResponses findById(long id);
    Iterable<UsersCheckboxResponses> findByCheckboxResponseId(long id);

}
