package com.vibent.vibentback.bubble.checkbox.entry;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vibent.vibentback.bubble.checkbox.CheckboxBubble;
import com.vibent.vibentback.bubble.checkbox.usersResponses.UsersCheckboxResponses;
import com.vibent.vibentback.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@SQLDelete(sql = "UPDATE checkbox_response SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class CheckboxResponse {

    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @PrimaryKeyJoinColumn
    private CheckboxBubble bubble;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "response", cascade = CascadeType.ALL)
    private Set<UsersCheckboxResponses> usersResponses;

    @ManyToOne
    @PrimaryKeyJoinColumn
    private User user;

    @NonNull
    private String content;

    @JsonIgnore
    private boolean deleted;
}