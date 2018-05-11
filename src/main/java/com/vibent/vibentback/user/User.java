package com.vibent.vibentback.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vibent.vibentback.api.eventParticipation.EventParticipationResponse;
import com.vibent.vibentback.api.eventParticipation.UserParticipationResponse;
import com.vibent.vibentback.eventParticipation.EventParticipation;
import com.vibent.vibentback.groupT.GroupT;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@SQLDelete(sql = "UPDATE user SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class User implements UserDetails, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String ref;
    private String email;
    private String firstName;
    private String lastName;
    @Column(insertable = false, updatable = false)
    private boolean deleted = false;

    // Authentication
    private String password;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = false;
    private Date lastPasswordReset;

    // Links
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<EventParticipation> participations;

    @JsonIgnore
    @ManyToMany(mappedBy = "members", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private Set<GroupT> memberships = new HashSet<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "admins", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private Set<GroupT> adminships = new HashSet<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "invites", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private Set<GroupT> inviteships = new HashSet<>();

    public void addMembership(GroupT group) {
        memberships.add(group);
        if (!group.getMembers().contains(this))
            group.addMember(this);
    }

    public void addAdminship(GroupT group) {
        adminships.add(group);
        if (!group.getAdmins().contains(this))
            group.addAdmin(this);
    }

    public void addInviteship(GroupT group) {
        inviteships.add(group);
        if (!group.getInvites().contains(this))
            group.addInvite(this);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
}