package com.vibent.vibentback.bubble.alimentation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vibent.vibentback.bubble.Bubble;
import com.vibent.vibentback.bubble.alimentation.entry.AlimentationEntry;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Set;

@Setter
@Getter
@Entity
@SQLDelete(sql = "UPDATE alimentation_bubble SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class AlimentationBubble extends Bubble {

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "bubble", cascade = CascadeType.ALL)
    private Set<AlimentationEntry> entries;

    @JsonProperty
    public Set<AlimentationEntry> getEntries(){
        return entries;
    }
}