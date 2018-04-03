package com.vibent.vibentback.bubble.checkbox.entry;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface CheckboxResponseRepository extends CrudRepository<CheckboxResponse, Long> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE checkbox_response SET deleted = FALSE WHERE id = :id", nativeQuery = true)
    int recover(@Param("id") long id);

    @Query(value = "SELECT deleted FROM checkbox_response WHERE id = :id", nativeQuery = true)
    boolean isDeleted(@Param("id") long id);

}
