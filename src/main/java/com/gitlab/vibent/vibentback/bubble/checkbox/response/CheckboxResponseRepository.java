package com.gitlab.vibent.vibentback.bubble.checkbox.response;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;


public interface CheckboxResponseRepository extends CrudRepository<CheckboxResponse, Long> {

    CheckboxResponse findById(long id);
    ArrayList<CheckboxResponse> findByBubbleId(long id);

}
