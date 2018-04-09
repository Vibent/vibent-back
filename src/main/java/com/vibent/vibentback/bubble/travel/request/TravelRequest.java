package com.vibent.vibentback.bubble.travel.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vibent.vibentback.bubble.travel.TravelBubble;
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
@SQLDelete(sql = "UPDATE travel_request SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class TravelRequest {

    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @PrimaryKeyJoinColumn
    private TravelBubble bubble;

    @ManyToOne
    @PrimaryKeyJoinColumn
    private User user;

    private Integer capacity;

    @JsonIgnore
    private Boolean isAttachedToProposal;

    @JsonIgnore
    private Boolean deleted;
}