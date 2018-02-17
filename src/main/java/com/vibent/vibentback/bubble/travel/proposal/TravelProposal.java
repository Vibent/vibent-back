package com.vibent.vibentback.bubble.travel.proposal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@SQLDelete(sql = "UPDATE travel_proposal SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class TravelProposal {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private Long bubbleId;
    @NonNull
    private String driverRef;
    private int capacity;
    @NonNull
    private String passByCities;
    @Column(insertable = false, updatable = false)
    @JsonIgnore
    private boolean deleted;
}