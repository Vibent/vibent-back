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
@RequiredArgsConstructor
@EqualsAndHashCode(of = {"user", "group"})
@SQLDelete(sql = "UPDATE membership SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Membership {

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

    @NonNull
    private Boolean admin;

    @org.hibernate.annotations.Generated(value = GenerationTime.INSERT)
    @Column(insertable = false, updatable = false)
    private Date date;
}
