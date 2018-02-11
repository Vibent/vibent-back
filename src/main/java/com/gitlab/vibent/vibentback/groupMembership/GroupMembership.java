package com.gitlab.vibent.vibentback.groupMembership;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class GroupMembership {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String userRef;
    @NonNull
    private String groupRef;
    private boolean isAdmin;
    private boolean isAccepted;

}