package com.vibent.vibentback.distributionlist.membership;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vibent.vibentback.distributionlist.DistributionList;
import com.vibent.vibentback.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Data
@Entity
@EqualsAndHashCode(of = "id")
@SQLDelete(sql = "UPDATE distribution_list_membership SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class DistributionListMembership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @PrimaryKeyJoinColumn
    private User user;

    @ManyToOne
    @JsonIgnore
    @PrimaryKeyJoinColumn
    private DistributionList distributionList;

    @Column(updatable = false)
    private boolean deleted;

    @JsonProperty
    public String getUserRef() {
        return user.getRef();
    }

    @JsonProperty
    public long getDistributionListId() {
        return distributionList.getId();
    }
}
