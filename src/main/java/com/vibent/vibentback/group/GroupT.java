package com.vibent.vibentback.group;

import com.vibent.vibentback.common.permission.Permissible;
import com.vibent.vibentback.event.Event;
import com.vibent.vibentback.group.membership.Membership;
import com.vibent.vibentback.group.membership.MembershipRequest;
import com.vibent.vibentback.user.User;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString(of = {"id", "ref", "name", "imagePath", "hasDefaultAdmin", "deleted"})
@NoArgsConstructor
@RequiredArgsConstructor
@SQLDelete(sql = "UPDATE group_t SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class GroupT implements Permissible {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String ref;

    @NonNull
    private String name;

    private String description;

    private String imagePath;

    private boolean hasDefaultAdmin;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "group", orphanRemoval = true)
    private Set<Event> events = new HashSet<>();

    @Column(insertable = false, updatable = false)
    private boolean deleted;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "group", orphanRemoval = true)
    private Set<Membership> memberships = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "group", orphanRemoval = true)
    private Set<MembershipRequest> requests = new HashSet<>();

    @Override
    public boolean canRead(User user) {
        return isMember(user);
    }

    @Override
    public boolean canWrite(User user) {
        return (this.isHasDefaultAdmin() && isMember(user)) || isAdmin(user);
    }

    @Override
    public boolean canWriteChildren(User user) {
        return this.canWrite(user);
    }

    public boolean isAdmin(User user) {
        return this.getMemberships().stream().anyMatch(m -> m.getUser().equals(user) && m.getAdmin());
    }

    public boolean isMember(User user){
        return this.getMemberships().stream().anyMatch(p -> p.getUser().equals(user));
    }
}