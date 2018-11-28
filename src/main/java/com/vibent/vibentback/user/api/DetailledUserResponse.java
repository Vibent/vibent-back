package com.vibent.vibentback.user.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vibent.vibentback.group.api.UserMembershipRequestResponse;
import com.vibent.vibentback.group.api.UserMembershipResponse;
import com.vibent.vibentback.event.api.UserParticipationResponse;
import com.vibent.vibentback.user.User;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
public class DetailledUserResponse {
    @JsonIgnore
    private User user;

    public DetailledUserResponse(User user) {
        this.user = user;
    }

    @JsonProperty
    public String getRef() {
        return user.getRef();
    }

    @JsonProperty
    public String getEmail() {
        return user.getEmail();
    }

    @JsonProperty
    public String getPhoneNumber() {
        return user.getPhoneNumber();
    }

    @JsonProperty
    public String getFirstName() {
        return user.getFirstName();
    }

    @JsonProperty
    public String getLastName() {
        return user.getLastName();
    }

    @JsonProperty
    public Set<UserParticipationResponse> getParticipations() {
        Set<UserParticipationResponse> participationResponses = new HashSet<>();
        user.getParticipations().forEach(e ->
                participationResponses.add(new UserParticipationResponse(e.getEventRef(), e.getAnswer())));
        return participationResponses;
    }

    @JsonProperty
    public Set<UserMembershipResponse> getMemberships() {
        Set<UserMembershipResponse> memberships = new HashSet<>();
        user.getMemberships().forEach(m ->
                memberships.add(new UserMembershipResponse(m.getGroup().getRef(), m.getAdmin())));
        return memberships;
    }

    @JsonProperty
    public Set<UserMembershipRequestResponse> getMembershipRequests() {
        Set<UserMembershipRequestResponse> requests = new HashSet<>();
        user.getRequests().forEach(r -> {
            requests.add(new UserMembershipRequestResponse(r.getGroup().getRef()));
        });
        return requests;
    }
}
