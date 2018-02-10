package com.gitlab.vibent.vibentback.user;

import org.springframework.data.repository.CrudRepository;

import javax.persistence.Table;
import java.util.UUID;

public interface UserRepository extends CrudRepository<User, Long> {

    User findById(long id);

    User findByRef(UUID ref);

    User findByEmail(String email);
}