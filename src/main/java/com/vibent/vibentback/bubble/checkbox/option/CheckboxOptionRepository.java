package com.vibent.vibentback.bubble.checkbox.option;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface CheckboxOptionRepository extends CrudRepository<CheckboxOption, Long> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE checkbox_option SET deleted = FALSE WHERE id = :id", nativeQuery = true)
    int recover(@Param("id") long id);

    @Query(value = "SELECT deleted FROM checkbox_option WHERE id = :id", nativeQuery = true)
    boolean isDeleted(@Param("id") long id);
}
