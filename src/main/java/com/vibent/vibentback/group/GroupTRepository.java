package com.vibent.vibentback.group;

import com.vibent.vibentback.common.util.CrudRefRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

public interface GroupTRepository extends CrudRefRepository<GroupT, Long> {

    Optional<GroupT> findById(long id);

    Optional<GroupT> findByRef(String ref);

    Set<GroupT> findByName(String name);

    @Transactional
    int deleteByRef(String ref);

    @Modifying
    @Transactional
    @Query(value = "UPDATE group_t SET deleted = FALSE WHERE ref = :ref", nativeQuery = true)
    int recover(@Param("ref") String ref);

    @Query(value = "SELECT deleted FROM group_t WHERE ref = :ref", nativeQuery = true)
    boolean isDeleted(@Param("ref") String ref);
}
