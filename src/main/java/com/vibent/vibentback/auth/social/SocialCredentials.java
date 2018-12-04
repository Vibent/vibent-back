package com.vibent.vibentback.auth.social;

import com.vibent.vibentback.auth.social.provider.Provider;
import com.vibent.vibentback.user.User;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@SQLDelete(sql = "UPDATE social_credentials SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class SocialCredentials {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @PrimaryKeyJoinColumn
    private User user;

    @NotNull
    @Enumerated
    private Provider provider;

    @NotNull
    private String providerId;

    @Column(updatable = false)
    private boolean deleted = false;
}
