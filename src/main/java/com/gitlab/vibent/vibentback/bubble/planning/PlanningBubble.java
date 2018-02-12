package com.gitlab.vibent.vibentback.bubble.planning;

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
public class PlanningBubble {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
}