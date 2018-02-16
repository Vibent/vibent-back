package com.vibent.vibentback.bubble.alimentation.entry;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;


@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class AlimentationEntry {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @NonNull
    @JsonIgnore // ignored because when returned it is already in bubble object
    private Long bubbleId;
    @NonNull
    private String name;
    private int totalRequested;
    private int totalCurrent;
    @NonNull
    @Enumerated(EnumType.STRING)
    private Type type;
    @JsonIgnore
    private boolean deleted;

    public enum Type {
        Food, Drink
    }

}