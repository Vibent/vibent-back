package com.gitlab.vibent.vibentback.bubble.alimentation;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class AlimentationBubble {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

}