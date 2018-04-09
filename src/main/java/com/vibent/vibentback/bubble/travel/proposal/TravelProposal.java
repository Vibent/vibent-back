package com.vibent.vibentback.bubble.travel.proposal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vibent.vibentback.bubble.travel.TravelBubble;
import com.vibent.vibentback.bubble.travel.request.TravelRequest;
import com.vibent.vibentback.user.User;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Set;

@Entity
@Setter
@Getter
@EqualsAndHashCode(of = "id")
@ToString(exclude = "bubble")
@SQLDelete(sql = "UPDATE travel_proposal SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class TravelProposal {

    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @PrimaryKeyJoinColumn
    private TravelBubble bubble;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "bubble", cascade = CascadeType.ALL)
    private Set<TravelRequest> requests;

    @ManyToOne
    @PrimaryKeyJoinColumn
    private User user;

    private Integer capacity;
    private String passByCities;

    @JsonIgnore
    private Boolean deleted;

}