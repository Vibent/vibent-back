package com.vibent.vibentback.bubble.checkbox.usersResponses;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface UsersCheckboxResponsesRepository extends CrudRepository<UsersCheckboxResponses, Long> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE users_checkbox_responses SET deleted = FALSE WHERE id = :id", nativeQuery = true)
    int recover(@Param("id") long id);

    @Query(value = "SELECT deleted FROM users_checkbox_responses WHERE id = :id", nativeQuery = true)
    boolean isDeleted(@Param("id") long id);

    @Query(value = "SELECT bubble.id FROM checkbox_bubble bubble\n" +
            "JOIN checkbox_response response ON bubble.id = response.bubble_id\n" +
            "JOIN users_checkbox_responses userResponse ON answer.id = userResponse.checkbox_response_id\n" +
            "WHERE userResponse.id = :id", nativeQuery = true)

    Long getBubbleId(@Param("id") Long id);

}
