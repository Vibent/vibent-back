package com.vibent.vibentback.user;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByRef(String ref);

    Optional<User> findByPhoneNumber(String phoneNumber);

    boolean existsByRef(String ref);

    @Transactional
    int deleteByRef(String ref);

    @Modifying
    @Transactional
    @Query(value = "UPDATE user SET deleted = FALSE WHERE ref = :ref", nativeQuery = true)
    int recover(@Param("ref") String ref);

    @Query(value = "SELECT deleted FROM user WHERE ref = :ref", nativeQuery = true)
    boolean isDeleted(@Param("ref") String ref);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}