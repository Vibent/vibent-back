package com.vibent.vibentback.api.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vibent.vibentback.api.eventParticipation.UserParticipationResponse;
import com.vibent.vibentback.groupT.GroupT;
import com.vibent.vibentback.user.User;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Slf4j
public class DetailledUserResponse {
    @JsonIgnore
    private User user;

    public DetailledUserResponse(User user){
        this.user = user;
    }

    @JsonProperty
    public String getRef(){
        return user.getRef();
    }

    @JsonProperty
    public String getEmail(){
        return user.getEmail();
    }

    @JsonProperty
    public String getPhoneNumber(){
        return user.getPhoneNumber();
    }

    @JsonProperty
    public String getFirstName(){
        return user.getFirstName();
    }

    @JsonProperty
    public String getLastName(){
        return user.getLastName();
    }

    @JsonProperty
    public Set<UserParticipationResponse> getParticipationRefs(){
        Set<UserParticipationResponse> participationResponses = new HashSet<>();
        user.getParticipations().forEach(e -> participationResponses.add(new UserParticipationResponse(e.getEventRef(), e.getAnswer())));
        return participationResponses;
    }

    @JsonProperty
    public Set<String> getMembershipRefs(){
        return user.getMemberships().stream().map(GroupT::getRef).collect(Collectors.toSet());
    }

    @JsonProperty
    public Set<String> getAdminshipRefs(){
        return user.getAdminships().stream().map(GroupT::getRef).collect(Collectors.toSet());
    }

    @JsonProperty
    public Set<String> getInviteshipsRefs(){
        return user.getInviteships().stream().map(GroupT::getRef).collect(Collectors.toSet());
    }
}
