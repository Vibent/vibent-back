package com.vibent.vibentback.bubble.survey.answer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@SQLDelete(sql = "UPDATE survey_answer SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class SurveyAnswer {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private Long bubbleId;
    @NonNull
    private String creatorRef;
    @NonNull
    private String content;
    @Column(insertable = false, updatable = false)
    @JsonIgnore
    private boolean deleted;

}