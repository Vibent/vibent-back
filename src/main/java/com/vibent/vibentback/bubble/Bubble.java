package com.vibent.vibentback.bubble;

import com.vibent.vibentback.event.Event;
import com.vibent.vibentback.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@MappedSuperclass
@Getter
@Setter
public abstract class Bubble {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="event_ref", referencedColumnName="ref", nullable=false)
    private Event event;

    @ManyToOne
    @JoinColumn(name="creator_ref", referencedColumnName="ref", nullable=false)
    private User creator;

    @Enumerated(EnumType.STRING)
    private BubbleType type;

    Boolean deleted;

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
