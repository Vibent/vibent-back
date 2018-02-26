package com.vibent.vibentback.bubble.alimentation.entry;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vibent.vibentback.bubble.alimentation.AlimentationBubble;
import com.vibent.vibentback.bubble.alimentation.bring.AlimentationBring;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Setter
@Getter
@ToString(exclude = "bubble")
@SQLDelete(sql = "UPDATE alimentation_entry SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class AlimentationEntry {

    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @PrimaryKeyJoinColumn
    private AlimentationBubble bubble;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "entry", cascade = CascadeType.ALL)
    private Set<AlimentationBring> brings;

    private String name;
    private Integer totalRequested;
    private Integer totalCurrent;
    @Enumerated(EnumType.STRING)
    private Type type;
    @JsonIgnore
    private Boolean deleted;

    @JsonIgnore
    public void setBrings(Set<AlimentationBring> brings){
        this.brings = brings;
    }
    @JsonProperty
    public Set<AlimentationBring> getBrings() {
        return brings;
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

    public enum Type {
        Food, Drink, Other
    }
}