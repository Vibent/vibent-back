package com.vibent.vibentback.user;

import com.vibent.vibentback.event.participation.EventParticipation;
import com.vibent.vibentback.group.membership.Membership;
import com.vibent.vibentback.group.membership.MembershipRequest;
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
    private String ref;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
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
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    private Set<EventParticipation> participations = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    private Set<Membership> memberships = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    private Set<MembershipRequest> requests = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }
}