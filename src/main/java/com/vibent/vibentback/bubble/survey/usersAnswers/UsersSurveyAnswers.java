package com.vibent.vibentback.bubble.survey.usersAnswers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vibent.vibentback.bubble.survey.answer.SurveyAnswer;
import com.vibent.vibentback.user.User;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter
@Setter
@SQLDelete(sql = "UPDATE users_survey_answers SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class UsersSurveyAnswers {

    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @PrimaryKeyJoinColumn
    private SurveyAnswer answer;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="user_ref", nullable=false)
    private User user;

    @JsonIgnore
    private boolean deleted;

}