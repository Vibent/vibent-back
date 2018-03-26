package com.vibent.vibentback.bubble;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vibent.vibentback.event.Event;
import com.vibent.vibentback.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Setter
@Getter
@EqualsAndHashCode(of = {"id", "type"})
@MappedSuperclass
public abstract class Bubble {
    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @PrimaryKeyJoinColumn
    private Event event;

    @ManyToOne
    @JsonIgnore
    @PrimaryKeyJoinColumn
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
}
