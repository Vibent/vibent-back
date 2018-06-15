package com.vibent.vibentback.groupT;

import com.vibent.vibentback.event.Event;
import com.vibent.vibentback.groupT.membership.Membership;
import com.vibent.vibentback.groupT.membership.MembershipRequest;
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
public class GroupT {

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
}