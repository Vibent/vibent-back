package com.vibent.vibentback.bubble;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vibent.vibentback.common.error.VibentError;
import com.vibent.vibentback.common.error.VibentException;
import com.vibent.vibentback.common.permission.Permissible;
import com.vibent.vibentback.event.Event;
import com.vibent.vibentback.user.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@EqualsAndHashCode(of = {"id"})
@MappedSuperclass
public abstract class Bubble implements Permissible {
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
    @JoinColumn(name = "user_id")
    private User creator;

    @JsonIgnore
    private boolean deleted;

    @JsonProperty
    public String getEventRef() {
        return event.getRef();
    }

    @JsonProperty
    public String getCreatorRef() {
        return creator.getRef();
    }

    @Override
    public boolean canRead(User user) {
        return this.event.canRead(user);
    }

    @Override
    public boolean canWrite(User user) {
        return this.event.canWrite(user);
    }

    @Override
    public boolean canWriteChildren(User user) {
        return this.canWrite(user);
    }
}
