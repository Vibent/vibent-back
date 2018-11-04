package com.vibent.vibentback.bubble.travel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vibent.vibentback.bubble.Bubble;
import com.vibent.vibentback.bubble.travel.proposal.TravelProposal;
import com.vibent.vibentback.bubble.travel.request.TravelRequest;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@SQLDelete(sql = "UPDATE travel_bubble SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class TravelBubble  extends Bubble {

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "bubble", cascade = CascadeType.ALL)
    private Set<TravelProposal> proposals = new HashSet<>();

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "bubble", cascade = CascadeType.ALL)
    private Set<TravelRequest> requests = new HashSet<>();

    @JsonProperty
    public Set<TravelProposal> getProposals(){
        return proposals;
    }

    @JsonProperty
    public Set<TravelRequest> getRequests(){
        return requests;
    }
}