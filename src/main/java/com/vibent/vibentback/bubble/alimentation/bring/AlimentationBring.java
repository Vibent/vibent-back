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
@Getter
@Setter
@SQLDelete(sql = "UPDATE alimentation_bring SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class AlimentationBring {

    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @PrimaryKeyJoinColumn
    private AlimentationEntry entry;

    @ManyToOne
    @PrimaryKeyJoinColumn
    private User user;

    private Integer quantity;

    @JsonIgnore
    private Boolean deleted;


    @JsonProperty
    public String getUserRef(){
        return user.getRef();
    }
}