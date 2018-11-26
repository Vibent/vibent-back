package com.vibent.vibentback.bubble.survey.option;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vibent.vibentback.bubble.survey.SurveyBubble;
import com.vibent.vibentback.bubble.survey.answer.SurveyAnswer;
import com.vibent.vibentback.common.error.VibentError;
import com.vibent.vibentback.common.error.VibentException;
import com.vibent.vibentback.common.permission.Permissible;
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
@SQLDelete(sql = "UPDATE survey_option SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class SurveyOption implements Permissible {

    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @PrimaryKeyJoinColumn
    private SurveyBubble bubble;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "option", cascade = CascadeType.ALL)
    private Set<SurveyAnswer> answers = new HashSet<>();

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

    @Override
    public boolean canRead(User user) {
        return this.getBubble().canRead(user);
    }

    @Override
    public boolean canWrite(User user) {
        return this.getBubble().canWrite(user);
    }

    @Override
    public boolean canWriteChildren(User user) {
        return this.canWrite(user);
    }
}