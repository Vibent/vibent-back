package com.vibent.vibentback.bubble.travel.proposal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vibent.vibentback.bubble.travel.TravelBubble;
import com.vibent.vibentback.bubble.travel.request.TravelRequest;
import com.vibent.vibentback.common.permission.Permissible;
import com.vibent.vibentback.user.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"bubble", "user"})
@SQLDelete(sql = "UPDATE travel_proposal SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class TravelProposal implements Permissible {

    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @PrimaryKeyJoinColumn
    private TravelBubble bubble;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "proposal", cascade = CascadeType.ALL)
    private Set<TravelRequest> attachedRequests = new HashSet<>();

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    private Integer capacity;

    private String passByCities;

    @JsonIgnore
    private boolean deleted;

    @JsonProperty
    public String getUserRef() {
        return user.getRef();
    }

    @Override
    public boolean canRead(User user) {
        return this.getBubble().canRead(user);
    }

    @Override
    public boolean canWrite(User user) {
        return this.getBubble().canWrite(user);
    }
}