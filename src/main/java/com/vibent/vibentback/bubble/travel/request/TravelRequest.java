package com.vibent.vibentback.bubble.travel.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vibent.vibentback.bubble.travel.TravelBubble;
import com.vibent.vibentback.bubble.travel.proposal.TravelProposal;
import com.vibent.vibentback.common.permission.Permissible;
import com.vibent.vibentback.user.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Setter
@Getter
@EqualsAndHashCode(of = "id")
@ToString(exclude = "user")
@SQLDelete(sql = "UPDATE travel_request SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class TravelRequest implements Permissible {

    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JsonIgnore
    @PrimaryKeyJoinColumn
    private TravelBubble bubble;

    @ManyToOne
    @JsonIgnore
    @PrimaryKeyJoinColumn
    private TravelProposal proposal;

    private Integer capacity;

    @JsonIgnore
    private boolean deleted;

    @JsonProperty
    public String getUserRef(){
        return user.getRef();
    }

    @Override
    public boolean canRead(User user) {
        return (this.getBubble() != null && this.getBubble().canRead(user))
                || (this.getProposal() != null && this.getProposal().canRead(user));
    }

    @Override
    public boolean canWrite(User user) {
        return (this.getBubble() != null && this.getBubble().canWrite(user))
                || (this.getProposal() != null && this.getProposal().canWrite(user));
    }
}