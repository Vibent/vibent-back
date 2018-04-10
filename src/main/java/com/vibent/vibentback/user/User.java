package com.vibent.vibentback.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vibent.vibentback.eventParticipation.EventParticipation;
import com.vibent.vibentback.groupT.GroupT;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@RequiredArgsConstructor
@SQLDelete(sql = "UPDATE user SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String ref;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @NonNull
    private String email;

    private String imagePath;

    @NonNull
    private String password;

    @NonNull
    private String salt;

    @JsonIgnore
    @Column(insertable = false, updatable = false)
    private Boolean deleted;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<EventParticipation> participations;

    @JsonIgnore
    @ManyToMany(mappedBy = "members", cascade = { CascadeType.ALL })
    private Set<GroupT> memberships = new HashSet<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "admins", cascade = { CascadeType.ALL })
    private Set<GroupT> adminships = new HashSet<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "invites", cascade = { CascadeType.ALL })
    private Set<GroupT> inviteships = new HashSet<>();

    public void addMembership(GroupT group){
        memberships.add(group);
        if(!group.getMembers().contains(this))
            group.addMember(this);
    }

    public void addAdminship(GroupT group){
        adminships.add(group);
        if(!group.getAdmins().contains(this))
            group.addAdmin(this);
    }

    public void addInviteship(GroupT group){
        inviteships.add(group);
        if(!group.getInvites().contains(this))
            group.addInvite(this);
    }
}