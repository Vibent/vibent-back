package com.vibent.vibentback.bubble.survey;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vibent.vibentback.bubble.Bubble;
import com.vibent.vibentback.bubble.survey.option.SurveyOption;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Set;

@Setter
@Getter
@Entity
@SQLDelete(sql = "UPDATE survey_bubble SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class SurveyBubble extends Bubble {

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "bubble", cascade = CascadeType.ALL)
    private Set<SurveyOption> options;

    private String title;

    @JsonProperty
    public Set<SurveyOption> getOptions(){
        return options;
    }
}