package com.vibent.vibentback.bubble.checkbox.answer;

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
@SQLDelete(sql = "UPDATE checkbox_answer SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class CheckboxAnswer {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private Long bubbleId;
    @NonNull
    private String content;
    @Column(insertable = false, updatable = false)
    @JsonIgnore
    private boolean deleted;
}