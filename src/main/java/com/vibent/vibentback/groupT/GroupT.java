package com.vibent.vibentback.groupT;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vibent.vibentback.eventParticipation.EventParticipation;
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
@NoArgsConstructor
@RequiredArgsConstructor
@SQLDelete(sql = "UPDATE group_t SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class GroupT {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String ref;

    @NonNull
    private String name;

    private String imagePath;

    private boolean hasDefaultAdmin;

    @JsonIgnore
    @Column(insertable = false, updatable = false)
    private boolean deleted;

    @JsonIgnore
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "group_membership",
            joinColumns = { @JoinColumn(name = "group_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") }
    )
    Set<User> members = new HashSet<>();

    @JsonIgnore
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "group_adminship",
            joinColumns = { @JoinColumn(name = "group_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") }
    )
    Set<User> admins = new HashSet<>();

    @JsonIgnore
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "group_inviteship",
            joinColumns = { @JoinColumn(name = "group_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") }
    )
    Set<User> invites = new HashSet<>();

    public void addMember(User user){
        members.add(user);
        if(!user.getMemberships().contains(this))
            user.addMembership(this);
    }

    public void addAdmin(User user){
        admins.add(user);
        if(!user.getAdminships().contains(this))
            user.addAdminship(this);
    }

    public void addInvite(User user){
        invites.add(user);
        if(!user.getInviteships().contains(this))
            user.addInviteship(this);
    }
}