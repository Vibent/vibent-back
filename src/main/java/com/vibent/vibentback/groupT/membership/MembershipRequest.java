package com.vibent.vibentback.groupT.membership;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vibent.vibentback.groupT.GroupT;
import com.vibent.vibentback.user.User;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(of = {"user", "group"})
@SQLDelete(sql = "UPDATE membership_request SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class MembershipRequest {


    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @ManyToOne
    @PrimaryKeyJoinColumn
    private User user;

    @NonNull
    @ManyToOne
    @PrimaryKeyJoinColumn
    private GroupT group;

    @Column(insertable = false, updatable = false)
    private boolean deleted;
}
