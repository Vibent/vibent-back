package com.vibent.vibentback.api.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vibent.vibentback.groupT.GroupT;
import com.vibent.vibentback.user.User;

import java.util.Set;
import java.util.stream.Collectors;


public class DetailledUserResponse {
    @JsonIgnore
    private User user;

    public DetailledUserResponse(User user){
        this.user = user;
    }

    @JsonProperty
    public String getUsername(){
        return user.getUsername();
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
    public String getFirstName(){
        return user.getFirstName();
    }

    @JsonProperty
    public String getLastName(){
        return user.getLastName();
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
