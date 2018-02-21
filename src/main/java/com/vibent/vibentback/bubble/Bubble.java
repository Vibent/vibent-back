package com.vibent.vibentback.bubble;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vibent.vibentback.event.Event;
import com.vibent.vibentback.user.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Setter
@Getter
@MappedSuperclass
public abstract class Bubble {
    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "event_ref", referencedColumnName = "ref", nullable = false)
    private Event event;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "creator_ref", referencedColumnName = "ref", nullable = false)
    private User creator;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private BubbleType type;

    @JsonIgnore
    private Boolean deleted;

    @JsonProperty
    public String getEventRef() {
        return event.getRef();
    }

    @JsonProperty
    public String getCreatorRef() {
        return creator.getRef();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bubble bubble = (Bubble) o;
        return Objects.equals(id, bubble.id) &&
                type == bubble.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type);
    }
}
