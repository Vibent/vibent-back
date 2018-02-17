package com.vibent.vibentback.user;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface UserRepository extends CrudRepository<User, Long> {

    User findById(long id);

    User findByRef(String ref);

    User findByEmail(String email);

    @Transactional
    int deleteByRef(String ref);

    @Modifying
    @Transactional
    @Query(value = "UPDATE user SET deleted = FALSE WHERE ref = :ref", nativeQuery = true)
    int recover(@Param("ref") String ref);

    @Query(value = "SELECT deleted FROM user WHERE ref = :ref", nativeQuery = true)
    boolean isDeleted(@Param("ref") String ref);
}