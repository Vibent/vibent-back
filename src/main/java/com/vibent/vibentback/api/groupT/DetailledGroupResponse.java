package com.vibent.vibentback.api.groupT;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vibent.vibentback.api.membership.GroupMembershipRequestResponse;
import com.vibent.vibentback.api.membership.GroupMembershipResponse;
import com.vibent.vibentback.api.membership.UserMembershipResponse;
import com.vibent.vibentback.api.participation.UserParticipationResponse;
import com.vibent.vibentback.groupT.GroupT;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
@Slf4j
@JsonInclude(JsonInclude.Include.ALWAYS)
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
                memberships.add(new GroupMembershipResponse(m.getUser().getRef(),m.getAdmin())));
        return memberships;
    }

    @JsonProperty
    public Set<GroupMembershipRequestResponse> getMembershipRequests() {
        Set<GroupMembershipRequestResponse> requests = new HashSet<>();
        group.getRequests().forEach(r ->
                requests.add(new GroupMembershipRequestResponse(r.getUser().getRef())));
        return requests;
    }
}