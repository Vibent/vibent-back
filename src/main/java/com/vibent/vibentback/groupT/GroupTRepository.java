package com.vibent.vibentback.groupT;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;



public interface GroupTRepository extends CrudRepository<GroupT, Long> {

    GroupT findById(long id);

    GroupT findByRef(String ref);

    Iterable<GroupT> findByName(String name);

    @Transactional
    int deleteByRef(String ref);

    @Modifying
    @Transactional
    @Query(value = "UPDATE user SET group_t = FALSE WHERE ref = :ref", nativeQuery = true)
    int recover(@Param("ref") String ref);

    @Query(value = "SELECT deleted FROM group_t WHERE ref = :ref", nativeQuery = true)
    boolean isDeleted(@Param("ref") String ref);
}
