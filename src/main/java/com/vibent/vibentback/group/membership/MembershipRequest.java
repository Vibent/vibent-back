package com.vibent.vibentback.group.membership;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vibent.vibentback.group.GroupT;
import com.vibent.vibentback.user.User;
import lombok.*;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
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

    @Column(updatable = false)
    private boolean deleted;

    @org.hibernate.annotations.Generated(value = GenerationTime.INSERT)
    @Column(updatable = false)
    private Date date;
}
