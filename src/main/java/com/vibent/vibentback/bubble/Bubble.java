package com.vibent.vibentback.bubble;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vibent.vibentback.event.Event;
import com.vibent.vibentback.user.User;

import javax.persistence.*;
import java.util.Objects;

@MappedSuperclass
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

    private Boolean deleted;

    @JsonProperty
    public Long getId() {
        return id;
    }

    @JsonIgnore
    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public Event getEvent() {
        return event;
    }

    @JsonProperty
    public String getEventRef(){
        return event.getRef();
    }

    @JsonIgnore
    public void setEvent(Event event) {
        this.event = event;
    }

    @JsonIgnore
    public User getCreator() {
        return creator;
    }

    @JsonProperty
    public String getCreatorRef(){
        return creator.getRef();
    }

    @JsonIgnore
    public void setCreator(User creator) {
        this.creator = creator;
    }

    @JsonIgnore
    public BubbleType getType() {
        return type;
    }

    @JsonIgnore
    public void setType(BubbleType type) {
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
