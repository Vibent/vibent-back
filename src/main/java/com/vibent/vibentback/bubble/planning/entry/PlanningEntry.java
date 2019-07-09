package com.vibent.vibentback.bubble.planning.entry;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vibent.vibentback.bubble.planning.PlanningBubble;
import com.vibent.vibentback.common.error.VibentError;
import com.vibent.vibentback.common.error.VibentException;
import com.vibent.vibentback.common.permission.Permissible;
import com.vibent.vibentback.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@SQLDelete(sql = "UPDATE planning_entry SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class PlanningEntry implements Permissible {

    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @PrimaryKeyJoinColumn
    private PlanningBubble bubble;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    @NonNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date start;

    @NonNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date end;

    @NonNull
    private String content;

    private boolean hasTime;

    @JsonIgnore
    private boolean deleted;

    @JsonProperty
    public String getUserRef() {
        return user.getRef();
    }

    @Override
    public boolean canRead(User user) {
        return this.getBubble().canRead(user);
    }

    @Override
    public boolean canWrite(User user) {
        return this.getBubble().canWrite(user);
    }

    @Override
    public boolean canWriteChildren(User user) {
        throw new VibentException(VibentError.ILLOGICAL_PERMISSION);
    }
}