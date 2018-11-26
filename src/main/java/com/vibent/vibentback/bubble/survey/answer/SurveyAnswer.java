package com.vibent.vibentback.bubble.survey.answer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vibent.vibentback.bubble.survey.option.SurveyOption;
import com.vibent.vibentback.common.error.VibentError;
import com.vibent.vibentback.common.error.VibentException;
import com.vibent.vibentback.common.permission.Permissible;
import com.vibent.vibentback.user.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@SQLDelete(sql = "UPDATE survey_answer SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class SurveyAnswer implements Permissible {
    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @PrimaryKeyJoinColumn
    private SurveyOption option;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    private boolean deleted;

    @JsonProperty
    public String getUserRef(){
        return user.getRef();
    }

    @Override
    public boolean canRead(User user) {
        return this.getOption().canRead(user);
    }

    @Override
    public boolean canWrite(User user) {
        return this.getOption().canWrite(user);
    }

    @Override
    public boolean canWriteChildren(User user) {
        throw new VibentException(VibentError.ILLOGICAL_PERMISSION);
    }
}