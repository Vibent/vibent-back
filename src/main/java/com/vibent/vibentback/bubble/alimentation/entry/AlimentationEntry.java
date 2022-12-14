package com.vibent.vibentback.bubble.alimentation.entry;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vibent.vibentback.bubble.alimentation.AlimentationBubble;
import com.vibent.vibentback.bubble.alimentation.bring.AlimentationBring;
import com.vibent.vibentback.common.error.VibentError;
import com.vibent.vibentback.common.error.VibentException;
import com.vibent.vibentback.common.permission.Permissible;
import com.vibent.vibentback.user.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@EqualsAndHashCode(of = "id")
@ToString(exclude = "bubble")
@SQLDelete(sql = "UPDATE alimentation_entry SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class AlimentationEntry implements Permissible {

    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @PrimaryKeyJoinColumn
    private AlimentationBubble bubble;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "entry", cascade = CascadeType.ALL)
    private Set<AlimentationBring> brings = new HashSet<>();

    private String name;
    private Integer totalRequested;

    @Enumerated(EnumType.STRING)
    private Type type;

    @JsonIgnore
    private boolean deleted;

    @JsonProperty
    public Set<AlimentationBring> getBrings() {
        return brings;
    }

    public void addBring(AlimentationBring bring){
        this.brings.add(bring);
    }

    @JsonProperty
    public int getCurrentBringing(){
        return brings.stream().mapToInt(AlimentationBring::getQuantity).sum();
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

    public enum Type {
        FOOD, DRINK, OTHER
    }
}