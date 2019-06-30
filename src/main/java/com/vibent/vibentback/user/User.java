package com.vibent.vibentback.user;

import com.vibent.vibentback.auth.social.SocialCredentials;
import com.vibent.vibentback.common.error.VibentError;
import com.vibent.vibentback.common.error.VibentException;
import com.vibent.vibentback.common.permission.Permissible;
import com.vibent.vibentback.event.participation.EventParticipation;
import com.vibent.vibentback.distributionlist.membership.DistributionListMembership;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@SQLDelete(sql = "UPDATE user SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class User implements UserDetails, Serializable, Permissible {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ref;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    @Column(updatable = false)
    private boolean deleted = false;
    private Date lastLogin;

    // Authentication
    private String password;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = false;
    private Date lastPasswordReset;
    private String profilePicLocation = null;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    private Set<SocialCredentials> credentials = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    private Set<EventParticipation> participations = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    private Set<DistributionListMembership> distributionListMemberships = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public boolean canRead(User user) {
        return this.equals(user);
    }

    @Override
    public boolean canWrite(User user) {
        return this.equals(user);
    }

    @Override
    public boolean canWriteChildren(User user) {
        throw new VibentException(VibentError.ILLOGICAL_PERMISSION);
    }
}