package com.gitlab.vibent.vibentback.bubble.ownership;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class BubbleOwnership {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String eventRef;
    @NonNull
    private Long bubbleId;
    @NonNull
    private String bubbleType;
    @NonNull
    private String creatorRef;
    private boolean isDeleted;
}