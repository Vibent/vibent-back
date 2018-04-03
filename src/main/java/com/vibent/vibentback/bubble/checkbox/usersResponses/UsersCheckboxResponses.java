package com.vibent.vibentback.bubble.checkbox.usersResponses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vibent.vibentback.bubble.checkbox.entry.CheckboxResponse;
import com.vibent.vibentback.user.User;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter
@Setter
@SQLDelete(sql = "UPDATE users_checkbox_responses SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class UsersCheckboxResponses {
    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @PrimaryKeyJoinColumn
    private CheckboxResponse response;

    @ManyToOne
    @PrimaryKeyJoinColumn
    private User user;

    @JsonIgnore
    private boolean deleted;

}