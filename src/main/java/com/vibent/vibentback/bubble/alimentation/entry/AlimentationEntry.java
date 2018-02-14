package com.vibent.vibentback.bubble.alimentation.entry;

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
public class AlimentationEntry {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private Long bubbleId;
    @NonNull
    private String name;
    private int totalRequested;
    private int totalCurrent;
    @NonNull
    private String type;
    private boolean isDeleted;

}