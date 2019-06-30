package com.vibent.vibentback.distributionlist;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vibent.vibentback.common.permission.Permissible;
import com.vibent.vibentback.distributionlist.membership.DistributionListMembership;
import com.vibent.vibentback.event.Event;
import com.vibent.vibentback.user.User;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Entity
@ToString(of = {"id", "title", "description", "deleted"})
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@SQLDelete(sql = "UPDATE distribution_list SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class DistributionList implements Serializable, Permissible {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String title;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "creator_id")
    private User creator;

    private String description;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Event> events = new HashSet<>();

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "distributionList", cascade = CascadeType.ALL)
    private Set<DistributionListMembership> memberships = new HashSet<>();

    @JsonIgnore
    @Column(updatable = false)
    private boolean deleted;

    @Override
    public boolean canRead(User user) {
        return this.memberships.stream().anyMatch(m -> m.getUser().equals(user));
    }

    @Override
    public boolean canWrite(User user) {
        return this.creator.equals(user);
    }

    @Override
    public boolean canWriteChildren(User user) {
        return this.canWrite(user);
    }

    @JsonProperty
    public String getCreatorRef() {
        return creator.getRef();
    }

    @JsonProperty
    public Set<String> getMemberRefs() {
        return memberships.stream().map(DistributionListMembership::getUserRef).collect(Collectors.toSet());
    }

    @JsonProperty
    public Set<String> getEventRefs() {
        return events.stream().map(Event::getRef).collect(Collectors.toSet());
    }
}