package com.gitlab.vibent.vibentback.user;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserRepository extends CrudRepository<User, Long> {

    User findById(long id);

    User findByRef(String ref);

    User findByEmail(String email);

    User deleteByRef(String ref);
}