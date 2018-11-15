package com.vibent.vibentback.common.util;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface CrudRefRepository<T, ID> extends CrudRepository<T, ID> {
    Optional<T> findByRef(String ref);
}
