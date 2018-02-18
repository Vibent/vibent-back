package com.vibent.vibentback.bubble.ownership;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vibent.vibentback.bubble.BubbleType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

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
    private BubbleType bubbleType;
    @NonNull
    private String creatorRef;
    @Column(insertable = false, updatable = false)
    @JsonIgnore
    private boolean deleted;

}