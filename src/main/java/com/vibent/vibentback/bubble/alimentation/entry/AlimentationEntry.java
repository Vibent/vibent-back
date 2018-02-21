package com.vibent.vibentback.bubble.alimentation.entry;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class AlimentationEntry {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="bubble_id", nullable=false)
    private AlimentationBubble bubble;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "entry", cascade = CascadeType.ALL)
    private Set<AlimentationBring> brings;

    private String name;
    private int totalRequested;
    private int totalCurrent;
    @Enumerated(EnumType.STRING)
    private Type type;
    private Boolean deleted;

    public Long getId() {
        return id;
    }

    @JsonIgnore
    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public AlimentationBubble getBubble() {
        return bubble;
    }

    @JsonIgnore
    public void setBubble(AlimentationBubble bubble) {
        this.bubble = bubble;
    }

    @JsonProperty
    public Set<AlimentationBring> getBrings() {
        return brings;
    }

    @JsonIgnore
    public void setBrings(Set<AlimentationBring> brings) {
        this.brings = brings;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalRequested() {
        return totalRequested;
    }

    public void setTotalRequested(int totalRequested) {
        this.totalRequested = totalRequested;
    }

    public int getTotalCurrent() {
        return totalCurrent;
    }

    public void setTotalCurrent(int totalCurrent) {
        this.totalCurrent = totalCurrent;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @JsonIgnore
    public Boolean getDeleted() {
        return deleted;
    }

    @JsonIgnore
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
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

    public enum Type {
        Food, Drink, Other
    }
}