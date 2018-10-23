package com.vibent.vibentback.bubble.planning;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vibent.vibentback.bubble.Bubble;
import com.vibent.vibentback.bubble.planning.entry.PlanningEntry;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@NoArgsConstructor
@SQLDelete(sql = "UPDATE planning_bubble SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class PlanningBubble extends Bubble {

    private String title;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "bubble", cascade = CascadeType.ALL)
    private Set<PlanningEntry> entries = new HashSet<>();

    @JsonProperty
    public Set<PlanningEntry> getEntries(){
        return entries;
    }
}