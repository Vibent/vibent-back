package com.vibent.vibentback.bubble.alimentation.entry;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vibent.vibentback.bubble.alimentation.AlimentationBubble;
import com.vibent.vibentback.bubble.alimentation.bring.AlimentationBring;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class AlimentationEntry {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="bubble_id", nullable=false)
    private AlimentationBubble bubble;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "entry", cascade = CascadeType.ALL)
    private Set<AlimentationBring> brings;

    private String name;
    private int totalRequested;
    private int totalCurrent;
    @Enumerated(EnumType.STRING)
    private Type type;
    Boolean deleted;

    public enum Type {
        Food, Drink, Other
    }

    public AlimentationEntry(AlimentationBubble bubble, String name, Type type) {
        this.bubble = bubble;
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString() {
        return "AlimentationEntry{" +
                "id=" + id +
                ", bubble=" + bubble.getId() +
                ", name='" + name + '\'' +
                ", totalRequested=" + totalRequested +
                ", totalCurrent=" + totalCurrent +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlimentationEntry entry = (AlimentationEntry) o;
        return Objects.equals(id, entry.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}