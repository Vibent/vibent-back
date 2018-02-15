package com.vibent.vibentback.bubble.checkbox.response;

import org.springframework.data.repository.CrudRepository;

public interface CheckboxResponseRepository extends CrudRepository<CheckboxResponse, Long> {

    CheckboxResponse findById(long id);
    Iterable<CheckboxResponse> findByBubbleId(long id);

}
