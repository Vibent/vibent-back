package com.vibent.vibentback.bubble.alimentation;

import com.vibent.vibentback.bubble.Bubble;
import com.vibent.vibentback.bubble.alimentation.entry.AlimentationEntry;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
public class AlimentationBubble extends Bubble {

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "bubble", cascade = CascadeType.ALL)
    private Set<AlimentationEntry> entries;

}