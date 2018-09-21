package com.vibent.vibentback.bubble.checkbox.option;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vibent.vibentback.bubble.checkbox.CheckboxBubble;
import com.vibent.vibentback.bubble.checkbox.answer.CheckboxAnswer;
import com.vibent.vibentback.user.User;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(of = "id")
@SQLDelete(sql = "UPDATE checkbox_option SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class CheckboxOption {

    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @PrimaryKeyJoinColumn
    private CheckboxBubble bubble;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "option", cascade = CascadeType.ALL)
    private Set<CheckboxAnswer> answers = new HashSet<>();

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    @NonNull
    private String content;

    @JsonIgnore
    private boolean deleted;

    @JsonProperty
    public String getUserRef(){
        return user.getRef();
    }
}