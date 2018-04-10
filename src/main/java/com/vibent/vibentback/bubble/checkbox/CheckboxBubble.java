package com.vibent.vibentback.bubble.checkbox;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vibent.vibentback.bubble.Bubble;
import com.vibent.vibentback.bubble.checkbox.option.CheckboxOption;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Set;

@Setter
@Getter
@Entity
@SQLDelete(sql = "UPDATE checkbox_bubble SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class CheckboxBubble extends Bubble{

    @NonNull
    private String title;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "bubble", cascade = CascadeType.ALL)
    private Set<CheckboxOption> options;

    @JsonProperty
    public Set<CheckboxOption> getOptions(){
        return options;
    }
}