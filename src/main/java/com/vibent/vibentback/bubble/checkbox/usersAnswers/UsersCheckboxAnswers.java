package com.vibent.vibentback.bubble.checkbox.usersAnswers;

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
@SQLDelete(sql = "UPDATE users_checkbox_answers SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class UsersCheckboxAnswers {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String userRef;
    @NonNull
    private Long checkboxAnswerId;
    @Column(insertable = false, updatable = false)
    @JsonIgnore
    private boolean deleted;

}