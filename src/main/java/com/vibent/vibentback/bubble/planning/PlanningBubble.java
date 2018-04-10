package com.vibent.vibentback.bubble.planning;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vibent.vibentback.bubble.Bubble;
import com.vibent.vibentback.bubble.planning.entry.PlanningEntry;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class PlanningBubble extends Bubble {

    private String title;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "bubble", cascade = CascadeType.ALL)
    private Set<PlanningEntry> entries;

    @JsonProperty
    public Set<PlanningEntry> getEntries(){
        return entries;
    }
}