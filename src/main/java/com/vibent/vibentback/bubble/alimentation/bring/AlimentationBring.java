package com.vibent.vibentback.bubble.alimentation.bring;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vibent.vibentback.bubble.alimentation.AlimentationBubble;
import com.vibent.vibentback.bubble.alimentation.entry.AlimentationEntry;
import com.vibent.vibentback.user.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
public class AlimentationBring {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="entry_id", nullable=false)
    private AlimentationEntry entry;
    @ManyToOne
    @JoinColumn(name="user_ref", nullable=false)
    private User user;
    private int quantity;
    private Boolean deleted;

    public Long getId() {
        return id;
    }

    @JsonIgnore
    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public AlimentationEntry getEntry() {
        return entry;
    }

    @JsonIgnore
    public void setEntry(AlimentationEntry entry) {
        this.entry = entry;
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }

    @JsonProperty
    public String getUserRef(){
        return user.getRef();
    }

    @JsonIgnore
    public void setUser(User user) {
        this.user = user;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @JsonIgnore
    public Boolean getDeleted() {
        return deleted;
    }

    @JsonIgnore
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}