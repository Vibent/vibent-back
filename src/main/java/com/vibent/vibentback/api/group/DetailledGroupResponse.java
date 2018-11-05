package com.vibent.vibentback.api.group;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vibent.vibentback.api.group.membership.GroupMembershipRequestResponse;
import com.vibent.vibentback.api.group.membership.GroupMembershipResponse;
import com.vibent.vibentback.group.GroupT;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

@Data
@Slf4j
public class DetailledGroupResponse {
    @JsonIgnore
    private GroupT group;

    public DetailledGroupResponse(GroupT group) {
        this.group = group;
    }

    @JsonProperty
    public String getRef() {
        return group.getRef();
    }

    @JsonProperty
    public String getName() {
        return group.getName();
    }

    @JsonProperty
    public String getDescription() {
        return group.getDescription();
    }

    @JsonProperty
    public String getImagePath() {
        return group.getImagePath();
    }

    @JsonProperty
    public Boolean hasDefaultAdmin() {
        return group.isHasDefaultAdmin();
    }

    @JsonProperty
    public Set<GroupMembershipResponse> getMemberships() {
        Set<GroupMembershipResponse> memberships = new HashSet<>();
        group.getMemberships().forEach(m ->
                memberships.add(new GroupMembershipResponse(m.getUser().getRef(), m.getAdmin(), m.getDate())));
        return memberships;
    }

    @JsonProperty
    public Set<GroupMembershipRequestResponse> getMembershipRequests() {
        Set<GroupMembershipRequestResponse> requests = new HashSet<>();
        group.getRequests().forEach(r ->
                requests.add(new GroupMembershipRequestResponse(r.getUser().getRef(), r.getDate())));
        return requests;
    }
}
